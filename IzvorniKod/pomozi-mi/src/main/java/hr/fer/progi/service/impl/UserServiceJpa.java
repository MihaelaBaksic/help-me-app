package hr.fer.progi.service.impl;

import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceJpa implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) {
        // TODO Assert what can and can't be null
        Assert.notNull(user, "User object must be given");
        // TODO email is unique, so I can check if database already has User with given email?
        String email = user.getEmail();
        Assert.hasText(email, "Email must be given");

        if (userRepository.countByEmail(user.getEmail()) > 0)
            // TODO change type of this exception
            throw new IllegalArgumentException("User with Email: " + user.getEmail() + " already exists");

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Assert.notNull(username, "Username must be given");
        return userRepository.findByUsername(username);
    }
}
