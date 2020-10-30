package hr.fer.progi.wrappers;

import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.rest.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {
    @Override
    public EntityModel<UserDTO> toModel(UserDTO userDTO) {
        return EntityModel.of(new UserDTO(userDTO.getUsername(), userDTO.getName(), userDTO.getSurname(), userDTO.getEmail(), userDTO.isAdministrator()),
                linkTo(methodOn(UserController.class).getUser(userDTO.getUsername())).withSelfRel());
    }

}
