package hr.fer.progi.service.impl;

import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.UserDTO;
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
        Assert.notNull(user, "User object must be given");
        String username = user.getEmail();
        Assert.hasText(username, "Username must be given");

        if (userRepository.countByUsername(user.getUsername()) > 0)
            throw new IllegalArgumentException("User with Username: " + user.getUsername() + " already exists");

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        // TODO check this method
        Assert.notNull(user, "User object must be given");
        String username = user.getUsername();
        Assert.hasText(username, "Username must be given");

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        Assert.notNull(username, "Username must be given");
        return userRepository.findByUsername(username);
    }


}
