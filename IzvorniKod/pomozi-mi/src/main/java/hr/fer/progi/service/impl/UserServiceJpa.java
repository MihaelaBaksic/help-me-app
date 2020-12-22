package hr.fer.progi.service.impl;


import hr.fer.progi.dao.AddressRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.UserService;
import hr.fer.progi.service.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
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
    private  RatingService ratingService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) {
    	assertData(user);

        if (userRepository.countByUsername(user.getUsername()) > 0)
            throw new IllegalArgumentException("User with Username: " + user.getUsername() + " already exists");
        if(userRepository.countByEmail(user.getEmail()) > 0) 
            throw new IllegalArgumentException("User with email: " + user.getEmail() + " already exists");
        if(userRepository.countByPhoneNumber(user.getPhoneNumber()) > 0)
            throw new IllegalArgumentException("User with phone number: " + user.getPhoneNumber() + " already exists");
        

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        assertData(user);
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

    @Override
    public List<User> getStatistics() {
        List<User> users = userRepository.findAll();

        Collections.sort(users, (User u1, User u2)-> {
            double u1Rating = ratingService.calculateAverageRatingForUser(u1.getUsername());
            System.out.println("U1 rating " + u1Rating);
            double u2Rating = ratingService.calculateAverageRatingForUser(u2.getUsername());
            System.out.println("U2 rating " + u1Rating);

            if(u1Rating != u2Rating)
                return u1Rating < u2Rating ? -1 : 1;

            int u1NoOfHandled = calculateNumberOfHandledRequests(u1);
            System.out.println("U1 number handled " + u1NoOfHandled);
            int u2NoOfHandled = calculateNumberOfHandledRequests(u2);
            System.out.println("U2 number handled " + u2NoOfHandled);

            if(u1NoOfHandled != u2NoOfHandled)
                return u1NoOfHandled < u2NoOfHandled ? -1 : 1;

            return u1.getId() > u2.getId() ? -1 : 1;
        });

        Collections.reverse(users);

        System.out.println(users);

        return users.size() <= 3 ? users : users.subList(0,3);

    }


    private int calculateNumberOfHandledRequests(User user){
        List<Request> handledRequests = userRepository.findAllHandledRequests(user.getUsername());
        return handledRequests==null ? 0 : handledRequests.size();
    }


    /**
     * Validates user.
     * @param user user to be asserted
     */
    private void assertData(User user) {
        Assert.notNull(user, "User object must be given");
        Assert.notNull(user.getEmail(), "Email must be given");
        Assert.notNull(user.getFirstName(), "First name must be given");
        Assert.notNull(user.getLastName(), "Last name must be given");
        Assert.notNull(user.getPhoneNumber(), "Phone number must be given");
        Assert.notNull(user.getUsername(), "Username must be given");
        Assert.notNull(user.getAddress().getLocationName(), "Location name must be given");
        Assert.notNull(user.getAddress().getStreetName(), "Street name must be given");
        Assert.notNull(user.getAddress().getStreetNumber(), "Street number must be given");
        Assert.notNull(user.getAddress().getZipCode(), "Zip code must be given");
    }
}

