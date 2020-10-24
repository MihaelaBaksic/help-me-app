package hr.fer.progi.rest;

import hr.fer.progi.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    //private UserService userService;

    @GetMapping("/{username}")
    public @ResponseBody User getUser(@PathVariable("username") String username){
        User user =  new User();
        //Find user by username
        return user;
    }

    @GetMapping("")
    public ArrayList<User> getUsers(){
        // dohvati iz baze sve usere
        return new ArrayList<User>();
    }

    //brisanje ili blokiranje usera - moze samo admin


}
