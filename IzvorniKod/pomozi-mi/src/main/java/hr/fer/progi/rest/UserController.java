package hr.fer.progi.rest;


import hr.fer.progi.domain.User;
import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return userService.findByUsername(username);
    }





}
