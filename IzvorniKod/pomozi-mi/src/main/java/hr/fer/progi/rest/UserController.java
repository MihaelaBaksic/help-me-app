package hr.fer.progi.rest;


import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.mappers.RegistrationDTO;
import hr.fer.progi.mappers.RequestDTO;
import hr.fer.progi.mappers.UpdateUserDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.exceptions.BlockingException;
import hr.fer.progi.service.exceptions.NonexistingObjectReferencedException;
import hr.fer.progi.service.UserService;
import hr.fer.progi.wrappers.RequestModelAssembler;
import hr.fer.progi.wrappers.UserModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


/**
 * Handles requests toward "/user", "/settings" and "/{username}" path.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private final UserModelAssembler assembler;

    @Autowired
    private RequestModelAssembler requestAssembler;

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
     * @param updateDTO Changes that currently logged user made
     * @return Http response entity containing UserDTO
     */
    @PostMapping("/settings")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateDTO) {

        PasswordEncoder encoder = webSecurity.getPasswordEncoder();
        String encodedPass = encoder.encode(updateDTO.getPassword());
        updateDTO.setPassword(encodedPass);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userService.findByUsername(username);
        existingUser.updateUserData(updateDTO);


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
            throw new NonexistingObjectReferencedException("User with username " + username + " doesn't exist");

        return assembler.toModel(u.mapToUserDTO());
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
            throw new NonexistingObjectReferencedException("No user with username" + username);
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
            throw new NonexistingObjectReferencedException("No user with username" + username);
        }
        if (!user.getStatus().equals(UserStatus.NOTBLOCKED)) {
            throw new BlockingException("User with username " + username + " is already blocked");
        }

        //deletion of all ACTNOANS and ACTANS requests authored by the user to be blocked
        requestService.deleteActiveAuthoredRequests(user);

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
            throw new NonexistingObjectReferencedException("No user with username" + username);
        }
        if (user.getStatus().equals(UserStatus.PERMABLOCK))
            throw new BlockingException("User with username " + username +
                    " is blocked permanently and cannot be unblocked");

        return assembler.toModel(userService.unblockUser(user).mapToUserDTO());
    }

    /**
     * Gets all requests where user with given username is author
     * @param username
     * @return all authored requests
     */
    @GetMapping("/authoredRequests/{username}")
    @Secured("ROLE_USER")
    public CollectionModel<EntityModel<RequestDTO>> getAuthoredRequests(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        if(user==null)
            throw new NonexistingObjectReferencedException("User doesn't exist");

        return requestAssembler.toCollectionModel(requestService.findAuthoredRequests(user)
            .stream().map(r -> r.mapToRequestDTO()).collect(Collectors.toList()));
    }

    /**
     * Returns all requests of current user that are operable
     * Operable requests have status ACTNOANS, ACTANS or ACCEPTED
     * @return all operable requests of current user
     */
    @GetMapping("/operableRequests")
    @Secured("ROLE_USER")
    public CollectionModel<EntityModel<RequestDTO>> getRequestsToOperate(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        return requestAssembler.toCollectionModel(requestService.findAuthoredRequests(user)
                .stream().filter(r -> r.getStatus()== RequestStatus.ACCEPTED
                        || r.getStatus()== RequestStatus.ACTNOANS
                        || r.getStatus()==RequestStatus.ACTANS)
                .map(r -> r.mapToRequestDTO())
                .collect(Collectors.toList()));
    }


    /**
     * Gets all requests where user with given username is handler
     * @param username
     * @return all handler requests
     */
    @GetMapping("/handlerRequests/{username}")
    public CollectionModel<EntityModel<RequestDTO>> getHandleRequests(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        if(user==null)
            throw new NonexistingObjectReferencedException("User doesn't exist");

        return requestAssembler.toCollectionModel(requestService.findHandlerRequests(user)
                .stream().map(r -> r.mapToRequestDTO()).collect(Collectors.toList()));
    }

    @PostMapping("/addressSettings")
    public EntityModel<UserDTO> updateUserAddress(@RequestBody Address newAddress){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);
        currentUser.setAddress(newAddress);

        return assembler.toModel(userService.updateUser(currentUser).mapToUserDTO());

    }
}
