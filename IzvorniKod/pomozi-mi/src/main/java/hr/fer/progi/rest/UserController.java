package hr.fer.progi.rest;


import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @Secured("ROLE_USER")
    public UserDTO getCurrentUser(@AuthenticationPrincipal User user){
        UserDTO userDTO = new UserDTO();

        //TODO handle as an exception
        if(user != null) userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    @GetMapping("/settings")
    @Secured("ROLE_USER")
    public User getUserSettings(@AuthenticationPrincipal User user){
        return user;
    }

    @PostMapping("/settings")
    @Secured("ROLE_USER")
    public String updateUser(@AuthenticationPrincipal User user){
        //TODO update user in database
        //test this
        return "redirect:/user";
    }

    @GetMapping("/{username}")
    @Secured("ROLE_USER")
    public User getUser(@PathVariable("username") String username){
        return userService.findByUsername(username);
    }

}
