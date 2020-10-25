package hr.fer.progi.rest;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

public class AppUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ( username.equals("admin"))
            return new User(username, "pass", commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        else throw new UsernameNotFoundException("No user " + username);
    }
}
