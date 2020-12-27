package hr.fer.progi.rest;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.RatingDTO;
import hr.fer.progi.mappers.ReturnRatingDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.*;
import hr.fer.progi.service.exceptions.InvalidRatingException;
import hr.fer.progi.service.exceptions.NonexistingUserReferencedException;
import hr.fer.progi.wrappers.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Handles requests toward "/rating" path.
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserModelAssembler userAssembler;

    /**
     * Handles a HTTP GET Request when user wants to know rating score of other user.
     *
     * @param username User whose ratings we want to know
     * @return List of all ratings where user is rated
     */
    // GetMapping can't be on path: "/{username}/" because path is occupied by: "/statistics"
    @GetMapping("/of/{username}")
    @Secured("ROLE_USER")
    public List<ReturnRatingDTO> getRatings(@PathVariable String username) {
        if (userService.findByUsername(username) == null)
            throw new NonexistingUserReferencedException("There is no User with username: " + username);

        return ratingService.userRatings(username)
                .stream()
                .map(rating -> rating.mapToReturnRatingDTO())
                .collect(Collectors.toList());
    }

    /**
     * Handles a HTTP GET Request when user wants to know rating of other user from 1.0 to 5.0
     *
     * @param username User whose ratings we want to know
     * @return rating of given user in range 1.0 to 5.0
     */
    @GetMapping("/average/{username}")
    @Secured("ROLE_USER")
    public ResponseEntity<Double> getRating(@PathVariable String username) {
        return ResponseEntity.ok(ratingService.calculateAverageRatingForUser(username));
    }


    /**
     * Handles a HTTP POST Request when user wants rate some other user.
     *
     * @param ratingDTO Rating score
     * @return
     */
    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<ReturnRatingDTO> placeRating(@RequestBody RatingDTO ratingDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String ratedUsername = ratingDTO.getRatedUsername();
        if (username.equals(ratedUsername))
            throw new InvalidRatingException("User can't rate himself");

        User loggedUser = userService.findByUsername(username);
        User ratedUser = userService.findByUsername(ratingDTO.getRatedUsername());
        if (ratedUser == null)
            throw new NonexistingUserReferencedException("There is no user with username: " + ratedUsername);

        Long requestId = ratingDTO.getRequestId();
        Request request = requestId == null ? null :
                requestService.getRequestById(requestId);

        Rating rating = new Rating(ratingDTO.getRating(), ratingDTO.getComment(),
                loggedUser, ratedUser, request);

        return ResponseEntity.ok(
                ratingService.addRating(rating)
                        .mapToReturnRatingDTO());
    }

    /**
     * @return collection model of top rated users
     */
    @GetMapping("/statistics")
    @Secured("ROLE_USER")
    public CollectionModel<EntityModel<UserDTO>> getStatistics() {

        List<User> bestUsers = userService.getStatistics();

        List<UserDTO> bestUsersDTO = new ArrayList<>();
        bestUsers.forEach(u -> bestUsersDTO.add(u.mapToUserDTO()));

        return userAssembler.toCollectionModel(bestUsersDTO);
    }


}
