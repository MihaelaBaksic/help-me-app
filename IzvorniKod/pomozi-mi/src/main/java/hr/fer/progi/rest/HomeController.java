package hr.fer.progi.rest;

import ch.qos.logback.core.net.SyslogOutputStream;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private UserService userService;

    @Autowired
    private WebSecurity webSecurity;

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody RegistrationDTO regDTO){
        PasswordEncoder encoder = webSecurity.getPasswordEncoder();
        String encodedPass = encoder.encode(regDTO.getPassword());
        regDTO.setPassword(encodedPass);
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
