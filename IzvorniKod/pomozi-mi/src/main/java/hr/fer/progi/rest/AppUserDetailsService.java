package hr.fer.progi.rest;


import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Value("${pomozi-mi.admin.password}")
    private String adminPasswordHash = "pass";

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, adminPasswordHash, authorities(username));
    }

    public List<GrantedAuthority> authorities(String username){

        if ("admin".equals(username))
            return commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER");

        hr.fer.progi.domain.User user = userService.findByUsername(username);

        if (user.isAdministrator())
            return commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER");
        else
            return commaSeparatedStringToAuthorityList("ROLE_USER");

    }
}
