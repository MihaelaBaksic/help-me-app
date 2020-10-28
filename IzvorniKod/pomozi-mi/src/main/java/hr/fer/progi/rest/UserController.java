package hr.fer.progi.rest;


import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.UserService;
import hr.fer.progi.wrappers.UserModelAssembler;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final UserModelAssembler assembler;

    public UserController(UserModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping("")
    @Secured("ROLE_USER")
    public EntityModel<User> getCurrentUser(@AuthenticationPrincipal User user){
            return assembler.toModel(user);
    }

    @GetMapping("/settings")
    @Secured("ROLE_USER")
    public EntityModel<User> getUserSettings(@AuthenticationPrincipal User user){
        return assembler.toModel(user);
    }

    @PostMapping("/settings")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, @AuthenticationPrincipal User user){
        // TODO update user in database
        EntityModel<User> entityModel = assembler.toModel(userService.updateUser(updatedUser)); // updateUser(which args) ???
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
        // return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    @Secured("ROLE_USER")
    public EntityModel<User> getUser(@PathVariable("username") String username){
        return assembler.toModel(userService.findByUsername(username));
    }

}
