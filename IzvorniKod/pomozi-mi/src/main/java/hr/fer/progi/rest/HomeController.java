package hr.fer.progi.rest;

import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.LoginDTO;
import hr.fer.progi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import hr.fer.progi.mappers.*;
@RestController
@RequestMapping("/")
public class HomeController {

    /*@GetMapping("")
    public RedirectView getUser(@AuthenticationPrincipal User user){
        if(user== null)
            return new RedirectView("/login");
        else
            return new RedirectView("/requests");
    }*/

    private UserService userService;

    @PostMapping("/register")
    ResponseEntity<User> register(RegistrationDTO regDTO){
        return ResponseEntity.ok(userService.registerUser(regDTO.mapToUser()));
    }

    /*@PostMapping("/login")
    ResponseEntity<User> login(LoginDTO login){
        //check login
        return ResponseEntity.ok(userService.loginUser(login));
    }*/

    /*@PostMapping("/logout")
    @Secured("ROLE_USER")
    ResponseEntity logout(@AuthenticationPrincipal User user){


    }*/

}
