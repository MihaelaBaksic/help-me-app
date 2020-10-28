package hr.fer.progi.rest;

import hr.fer.progi.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HomeController {

    // ovisi o autentikaciji, bit ce napisano nakon autentikacije i autorizacija

    @GetMapping("")
    public RedirectView getUser(@AuthenticationPrincipal User user){
        if(user== null)
            return new RedirectView("/login");
        else
            return new RedirectView("/requests");
    }

}
