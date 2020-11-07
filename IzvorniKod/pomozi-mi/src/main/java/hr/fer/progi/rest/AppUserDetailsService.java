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
    private final String adminPasswordHash = "pass";

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));
        user.orElseThrow(()-> new UsernameNotFoundException("User not found : " + username));

        org.springframework.security.core.userdetails.User us =  new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), true, true, true, true,
                getGrantedAuthorities(user.get()));
        System.out.println(us);
        return us;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities;

        if (user.isAdministrator())
            return commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER");
        else
            return commaSeparatedStringToAuthorityList("ROLE_USER");

    }
}
