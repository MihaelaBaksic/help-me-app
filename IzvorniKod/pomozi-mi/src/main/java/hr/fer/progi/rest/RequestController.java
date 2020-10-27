package hr.fer.progi.rest;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.CreateRequestDTO;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.wrappers.RequestModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("")
    @Secured("ROLE_USER")
    public CollectionModel<EntityModel<Request>> getRequests() {
        return assembler.toCollectionModel(requestService.listAll());
    }

    @GetMapping("/{id}") //TODO handle exception if no request is found
    public EntityModel<Request> getRequest(@PathVariable("id") Long id){
        return assembler.toModel(requestService.getRequestById(id));
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<Request> createRequest(@RequestBody CreateRequestDTO createRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(requestService.addRequest(createRequest.mapToRequest(user)));
    }

}
