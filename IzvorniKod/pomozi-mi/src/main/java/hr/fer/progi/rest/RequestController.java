package hr.fer.progi.rest;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.CreateRequestDTO;
import hr.fer.progi.mappers.RequestDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.BlockingException;
import hr.fer.progi.service.InvalidRequestException;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.UserService;
import hr.fer.progi.wrappers.RequestModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

/**
 * Handles requests toward "/requests" and "/requests/{id}" path.
 */
@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserService userService;

    @Autowired
    private final RequestModelAssembler assembler;

    public RequestController(RequestModelAssembler assembler) {
        this.assembler = assembler;
    }


    /**
     * Returns all requests in system encapsulated in a model.
     *
     * @return all requests encapsulated in a model
     */
    @GetMapping("")
    @Secured("ROLE_USER")
    public CollectionModel<EntityModel<RequestDTO>> getRequests() {
        return assembler.toCollectionModel(
                requestService.listAll()
                .stream()
                .filter(r -> (r.getStatus() == RequestStatus.ACTNOANS || r.getStatus() == RequestStatus.ACTANS))
                .map(Request::mapToRequestDTO)
                .collect(Collectors.toList()));
    }

    /**
     * Gets one request whose id equals given id.
     *
     * @param id Id of request we want to find
     * @return request whose id was given
     */
    @GetMapping("/{id}") //TODO handle exception if no request is found
    public EntityModel<RequestDTO> getRequest(@PathVariable("id") Long id) {
    	Request r = requestService.getRequestById(id);
    	if(r == null || !(r.getStatus() == RequestStatus.ACTNOANS || r.getStatus() == RequestStatus.ACTANS))
    		throw new InvalidRequestException("Request is either nonexisting or nonactive.");
        return assembler.toModel(requestService.getRequestById(id)
                .mapToRequestDTO());
    }

    /**
     * Handles HTTP POST Request when user wants to create new request.
     *
     * @param createRequest DTO that contains all necessary data for creating new request.
     * @return
     */
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<Request> createRequest(@RequestBody CreateRequestDTO createRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(requestService.addRequest(createRequest
                .mapToRequest(userService.findByUsername(username))));
    }
    
    
    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_USER")
    @Transactional
    public ResponseEntity<?> deleteRequest(@PathVariable("id") Long id) {
    	
    	return ResponseEntity.ok(requestService.deleteRequest(id));
    }
    
    
    @PostMapping("/settings/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateRequest(@RequestBody RequestDTO createRequest, @PathVariable("id") Long id) {
    	Request r = requestService.getRequestById(id);
    	r.updateRequest(createRequest.mapToRequest());
    	
    	EntityModel<RequestDTO> model = assembler.toModel(requestService.updateRequest(r).mapToRequestDTO());
    	
    	return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri())
    			.body(model);
    }
    
    
    
    @PostMapping("/blockRequest/{id}")
    @Secured("ROLE_ADMIN")
    public EntityModel<RequestDTO> blockRequest(@PathVariable("id") Long id) {
    	Request r = requestService.getRequestById(id);
    	
    	if(r == null) {
    		throw new InvalidRequestException("Request does not exist.");
    	}
    	if(r.getStatus() == RequestStatus.BLOCKED) {
    		throw new BlockingException("Request with id " + id + " is already blocked");
    	}
    	
    	return assembler.toModel(requestService.blockRequest(r).mapToRequestDTO());
    }
    
    
    
    @PostMapping("/respond/{id}")
    @Secured("ROLE_USER")
    public EntityModel<RequestDTO> respondToRequest(@PathVariable("id") Long id) {
    	Request r = requestService.getRequestById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User potentialHandler = userService.findByUsername(username);
        
        return assembler.toModel(requestService.requestRespond(r, potentialHandler).mapToRequestDTO());
    }
    
    
    
    @PostMapping("/pickHandler/{id}")
    @Secured("ROLE_USER")
    public EntityModel<RequestDTO> pickHandler(@PathVariable("id") Long id, @RequestBody UserDTO handlerDTO) {
    	Request r = requestService.getRequestById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userService.findByUsername(username);
        if(r.getRequestAuthor() != current) {
        	throw new InvalidRequestException("You cant pick handler for request you did not create!");
        }
    	System.out.println(handlerDTO.getUsername());
    	User handler = userService.findByUsername(handlerDTO.getUsername());
    	
    	return assembler.toModel(requestService.pickRequestHandler(r, handler).mapToRequestDTO());
    }
    
    
    
    
    
    
    
    

}
