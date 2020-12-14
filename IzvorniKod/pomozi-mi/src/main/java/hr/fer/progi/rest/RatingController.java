package hr.fer.progi.rest;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.RatingDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Handles a HTTP GET Request when user wants to know rating score of other user.
     *
     * @param userDTO User whose ratings we want to know
     * @return List of all ratings where user is rated
     */
    @GetMapping("")
    @Secured("ROLE_USER")
    public List<Rating> getRatings(@RequestBody UserDTO userDTO) {
        return ratingService.userRatings(userDTO.getUsername());
    }

    /**
     * Handles a HTTP GET Request when user wants to know rating of other user from 1.0 to 5.0
     *
     * @param username User whose ratings we want to know
     * @return rating of given user in range 1.0 to 5.0
     */
    // TODO Rename and change path
    @GetMapping("/average/{username}")
    @Secured("ROLE_USER")
    public ResponseEntity<Double> getRating(@PathVariable String username) {
        // TODO Check if username in databse HERE, if not throw Exception (UnexistingUserReference)
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
    public ResponseEntity<Rating> placeRating(@RequestBody RatingDTO ratingDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = userService.findByUsername(username);
        User ratedUser = userService.findByUsername(ratingDTO.getRatedUsername());

        if(ratingDTO.getRequestId() == null)
            throw new InvalidRatingException("Rating id can't be null.");

        Request request = requestService.getRequestById(ratingDTO.getRequestId());

        // TODO make another RatingDTOreturn (not create)
        // TODO (all that is in RatingDTO (UserDTO who is rating and UserDTO rated and Request DTO if it references some Request))
        // TODO It will return ResponseEntity<RatingDTO>
        Rating rating = new Rating(ratingDTO.getRating(), ratingDTO.getComment(),
                loggedUser, ratedUser, request);

        return ResponseEntity.ok(ratingService.addRating(rating));
    }
}
