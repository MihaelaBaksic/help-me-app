package hr.fer.progi.service;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.LoginDTO;
import hr.fer.progi.mappers.UserDTO;

import java.util.List;

public interface UserService {

    /**
     * Lists all users in the system.
     * @return All users in the system
     */
    List<User> listAll();

    /**
     * Registers new {@link User} and returns that {@link User}.
     * @param user to register
     * @return newly registered {@link User}
     */
    User registerUser(User user);

    User updateUser(User user);

    /**
     * Finds {@link User} in the system by its property username.
     * @param username by which to search {@link User}
     * @return found {@link User}
     * @throws IllegalArgumentException if username is null
     */
    User findByUsername(String username);


    User loginUser(LoginDTO loginDTO);

}
