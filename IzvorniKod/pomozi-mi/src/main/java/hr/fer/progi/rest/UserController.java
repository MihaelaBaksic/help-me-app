package hr.fer.progi.rest;


import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.mappers.RegistrationDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.BlockingException;
import hr.fer.progi.service.UnexistingUserReferencedException;
import hr.fer.progi.service.UserService;
import hr.fer.progi.wrappers.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * Handles requests toward "/user", "/settings" and "/{username}" path.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final UserModelAssembler assembler;

    @Autowired
    private WebSecurity webSecurity;

    public UserController(UserModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping("/getCurrentUser")
    @Secured("ROLE_USER")
    public EntityModel<UserDTO> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        return assembler.toModel(user.mapToUserDTO());
    }


    /**
     * Handles a HTTP POST Request when user wants to update some information in their profile.
     *
     * @param regDTO Changes that currently logged user made
     * @return Http response entity containing UserDTO
     */
    @PostMapping("/settings")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateUser(@RequestBody RegistrationDTO regDTO) {

        PasswordEncoder encoder = webSecurity.getPasswordEncoder();
        String encodedPass = encoder.encode(regDTO.getPassword());
        regDTO.setPassword(encodedPass);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userService.findByUsername(username);
        existingUser.updateUserData(regDTO.mapToUser());


        EntityModel<UserDTO> entityModel = assembler.toModel(userService.updateUser(existingUser).mapToUserDTO());
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    /**
     * Gets current user.
     *
     * @param username Username of user to fetch
     * @return UserDTO
     */
    @GetMapping("/{username}")
    @Secured("ROLE_USER")
    public EntityModel<UserDTO> getUser(@PathVariable("username") String username) {
        User u = userService.findByUsername(username);
        if (u == null)
            throw new UnexistingUserReferencedException("User with username " + username + " doesn't exist");

        return assembler.toModel(u.mapToUserDTO());
    }


    /**
     * Handles a HTTP DELETE Request when user wants to delete their account
     *
     * @return response indicating success of the deleting
     */
    @PostMapping("/delete")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deleteUser() {
        //get the current user object
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.deleteUser(username)==1 ?
                ResponseEntity.ok("User deleted successfully") : ResponseEntity.badRequest().body("User can't be deleted");
    }

    /**
     * Sets User as administrator
     *
     * @param username Username of user that is to be set as administrator
     * @return UserDTO     Newly updated user
     */
    @PostMapping("/setAdmin/{username}")
    @Secured("ROLE_ADMIN")
    public EntityModel<UserDTO> setUserAsAdmin(@PathVariable("username") String username) {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnexistingUserReferencedException("No user with username" + username);
        }
        return assembler.toModel(userService.setUserAsAdmin(user).mapToUserDTO());
    }

    /**
     * User blocking
     *
     * @param username Username of user that is to be blocked
     * @return UserDTO     Newly updated user
     */
    @PostMapping("/blockUser/{username}")
    @Secured("ROLE_ADMIN")
    public EntityModel<UserDTO> blockUser(@PathVariable("username") String username, @RequestBody Boolean permanently) {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnexistingUserReferencedException("No user with username" + username);
        }
        if (!user.getStatus().equals(UserStatus.NOTBLOCKED)) {
            throw new BlockingException("User with username " + username + " is already blocked");
        }
        return assembler.toModel(userService.blockUser(user, permanently).mapToUserDTO());
    }

    /**
     * User unblocking
     * User can only be unblocked if he is not permanently blocked
     *
     * @param username Username of user that is to be unblocked
     * @return UserDTO     Newly updated user
     */
    @PostMapping("/unblockUser/{username}")
    @Secured("ROLE_ADMIN")
    public EntityModel<UserDTO> unblockUser(@PathVariable("username") String username) {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UnexistingUserReferencedException("No user with username" + username);
        }
        if (user.getStatus().equals(UserStatus.PERMABLOCK))
            throw new BlockingException("User with username " + username +
                    " is blocked permanently and cannot be unblocked");

        return assembler.toModel(userService.unblockUser(user).mapToUserDTO());
    }
}
