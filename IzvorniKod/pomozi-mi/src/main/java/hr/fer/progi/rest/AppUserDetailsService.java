package hr.fer.progi.rest;


import hr.fer.progi.domain.User;
import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Value("${pomozi-mi.admin.password}")
    private String adminPasswordHash = "pass";

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));

        user.orElseThrow(()-> new UsernameNotFoundException("User not found : " + username));

        System.out.println("THE USER IS FOUND" + username);
        return new MyUserDetails(user.get());
    }
}
