package hr.fer.progi.wrappers;

import hr.fer.progi.domain.Request;
import hr.fer.progi.rest.RequestController;
import hr.fer.progi.rest.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RequestModelAssembler implements RepresentationModelAssembler<Request, EntityModel<Request>> {

    @Override
    public EntityModel<Request> toModel(Request request) {
        return EntityModel.of(request,
                linkTo(methodOn(RequestController.class).getRequest(request.getId())).withSelfRel());
    }

    public CollectionModel<EntityModel<Request>> toCollectionModel(List<Request> requests) {
        List<EntityModel<Request>> entitiyModels  = requests.stream() //
                .map(this::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(entitiyModels, linkTo(methodOn(RequestController.class).getRequests()).withSelfRel());
    }
}
