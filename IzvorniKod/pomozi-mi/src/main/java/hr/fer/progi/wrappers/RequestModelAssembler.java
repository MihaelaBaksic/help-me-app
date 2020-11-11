package hr.fer.progi.wrappers;

import hr.fer.progi.domain.Request;
import hr.fer.progi.mappers.CreateRequestDTO;
import hr.fer.progi.mappers.RequestDTO;
import hr.fer.progi.rest.RequestController;
import hr.fer.progi.rest.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Converts a domain object, {@link Request} in this case, to {@link RepresentationModel}.
 */
@Component
public class RequestModelAssembler implements RepresentationModelAssembler<RequestDTO, EntityModel<RequestDTO>> {

    @Override
    public EntityModel<RequestDTO> toModel(RequestDTO requestDTO) {
        if (requestDTO.getDuration() == null)
            requestDTO.setDuration(new Time(7*24));

        return EntityModel.of(new RequestDTO(requestDTO.getId(), requestDTO.getDuration(), requestDTO.getComment(), requestDTO.getAddress(), requestDTO.getRequestAuthor()),
                linkTo(methodOn(RequestController.class).getRequest(requestDTO.getId())).withSelfRel());
    }

    /**
     * Wraps list of {@link RequestDTO}-s into {@link CollectionModel} instance.
     * @param requestsDTO
     * @return List of requestDTO-s wrapped in {@link CollectionModel}.
     */
    public CollectionModel<EntityModel<RequestDTO>> toCollectionModel(List<RequestDTO> requestsDTO) {
        List<EntityModel<RequestDTO>> entityModels  = requestsDTO.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entityModels,
                linkTo(methodOn(RequestController.class).getRequests()).withSelfRel());
    }
}
