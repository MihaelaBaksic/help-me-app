package hr.fer.progi.rest;

import ch.qos.logback.core.net.SyslogOutputStream;
import hr.fer.progi.dao.AddressRepository;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.UserService;
import hr.fer.progi.wrappers.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import hr.fer.progi.mappers.*;
@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler assembler;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private WebSecurity webSecurity;

    @CrossOrigin
    @PostMapping("/register")
    ResponseEntity<EntityModel<UserDTO>> register(@RequestBody RegistrationDTO regDTO){
        PasswordEncoder encoder = webSecurity.getPasswordEncoder();
        String encodedPass = encoder.encode(regDTO.getPassword());
        regDTO.setPassword(encodedPass);

        return ResponseEntity.ok(assembler.toModel(userService.registerUser(regDTO.mapToUser()).mapToUserDTO()));
    }

}
