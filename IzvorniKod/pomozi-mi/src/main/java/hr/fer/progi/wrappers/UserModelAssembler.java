package hr.fer.progi.wrappers;

import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.rest.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<UserDTO>> {
    @Override
    public EntityModel<UserDTO> toModel(User user) {
        return EntityModel.of(new UserDTO(user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.isAdministrator()),
                linkTo(methodOn(UserController.class).getUser(user.getUsername())).withSelfRel());
    }
}
