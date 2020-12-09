package hr.fer.progi.service.impl;


import hr.fer.progi.dao.AddressRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;

/**
 * Implementation of {@link UserServiceJpa} interface.
 */
@Service
public class UserServiceJpa implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) {
        Assert.notNull(user, "User object must be given");

        if (userRepository.countByUsername(user.getUsername()) > 0)
            throw new IllegalArgumentException("User with Username: " + user.getUsername() + " already exists");

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Assert.notNull(user, "User object must be given");
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Assert.notNull(username, "Username must be given");

        return userRepository.findByUsername(username);
    }

    @Override
    public long deleteUser(String username) {
        return userRepository.deleteByUsername(username);
    }

    @Override
    public User setUserAsAdmin(User user){
        user.setAdministrator(true);
        return userRepository.save(user);
    }

    public User blockUser(User user, Boolean permanently){
        System.out.println(permanently);
        System.out.println(permanently.getClass().getName());

        if(permanently)
            user.setStatus(UserStatus.PERMABLOCK);
        else
            user.setStatus(UserStatus.TEMPBLOCK);


        System.out.println(user);
        User u = userRepository.save(user);
        System.out.println(u);
        return u;
    }

    public User unblockUser(User user)
    {
        user.setStatus(UserStatus.NOTBLOCKED);
        return userRepository.save(user);
    }
}

