package hr.fer.progi.service;

import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.rest.HomeController;
import hr.fer.progi.rest.UserController;

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
     * @param user user whose data will be updated
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
     * Deletes user's account.
     *
     * @param username  of user whose account is to be deleted
     * @return number of object deleted from database
     */
    // TODO check which is better to write: that services use User or just username ?
    long deleteUser(String username);

    User setUserAsAdmin(User user);

    User blockUser(User user, Boolean permanently);

    User unblockUser(User user);

}
