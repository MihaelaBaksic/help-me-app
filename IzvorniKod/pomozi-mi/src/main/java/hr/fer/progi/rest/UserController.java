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
    public EntityModel<UserDTO> getCurrentUser(@AuthenticationPrincipal User user){
            return assembler.toModel(user.mapToUserDTO());
    }

    @GetMapping("/settings")
    @Secured("ROLE_USER")
    public EntityModel<UserDTO> getUserSettings(@AuthenticationPrincipal User user){
        return assembler.toModel(user.mapToUserDTO());
    }

    @PostMapping("/settings")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, @AuthenticationPrincipal User user){
        // TODO update user in database
        EntityModel<UserDTO> entityModel = assembler.toModel(userService.updateUser(updatedUser).mapToUserDTO());
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
        // return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    @Secured("ROLE_USER")
    public EntityModel<UserDTO> getUser(@PathVariable("username") String username){
        return assembler.toModel(userService.findByUsername(username).mapToUserDTO());
    }

    @DeleteMapping("/{username}")
    @Secured("ROLE_USER")
    public ResponseEntity<String> deleteUser(@RequestBody String username) {
        return userService.deleteUser(username) ?
                ResponseEntity.ok("User deleted successfully") : ResponseEntity.badRequest().body("User can't be deleted");
    }


}
