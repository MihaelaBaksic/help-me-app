package hr.fer.progi.rest;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.CreateRequestDTO;
import hr.fer.progi.mappers.RequestDTO;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.wrappers.RequestModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Handles requests toward "/requests" and "/requests/{id}" path.
 */
@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

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
        return assembler.toModel(requestService.getRequestById(id)
                .mapToRequestDTO());
    }

    /**
     * Handles HTTP POST Request when user wants to create new request.
     *
     * @param createRequest DTO that contains all necessary data for creating new request.
     * @param user          User that wants to create new request
     * @return
     */
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<Request> createRequest(@RequestBody CreateRequestDTO createRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(requestService.addRequest(createRequest
                .mapToRequest(user)));
    }

}
