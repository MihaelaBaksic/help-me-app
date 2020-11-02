package hr.fer.progi.rest;

import hr.fer.progi.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user){
        this.user=user;
        System.out.println("User: " + user.getUsername() + "Pass " + user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        System.out.println("GETTING AUTHORITIES ");
        List<GrantedAuthority> list;

        if (user.isAdministrator())
            list =  commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER");
        else
            list = commaSeparatedStringToAuthorityList("ROLE_USER");

        System.out.println(list);
        return list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
