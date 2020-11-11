package hr.fer.progi.service;

import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.LoginDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.rest.HomeController;
import hr.fer.progi.rest.UserController;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Represents connection between {@link HomeController} and {@link UserRepository} and connection between {@link
 * UserController} and {@link UserRepository}.
 */
public interface UserService {

    /**
     * Lists all users in the system.
     *
     * @return All users in the system
     */
    List<User> listAll();

    /**
     * Registers new {@link User} and returns that {@link User}.
     *
     * @param user to register
     * @return newly registered {@link User}
     */
    User registerUser(User user);

    /**
     * Updates data for user.
     *
     * @param user
     * @return newly updated user
     */
    User updateUser(User user);

    /**
     * Finds {@link User} in the system by its property username.
     *
     * @param username by which to search {@link User}
     * @return found {@link User}
     * @throws IllegalArgumentException if username is null
     */
    User findByUsername(String username);


    /**
     * Logins user.
     *
     * @param loginDTO
     * @return Logged user.
     */
    // TODO loginUser accepts Login and not LoginDTO
    User loginUser(LoginDTO loginDTO);


    /**
     * Deletes user's account.
     *
     * @param username
     * @return true if account has been deleted, false otherwise.
     */
    // TODO check which is better to write: that services use User or just username ?
    boolean deleteUser(String username);

}
