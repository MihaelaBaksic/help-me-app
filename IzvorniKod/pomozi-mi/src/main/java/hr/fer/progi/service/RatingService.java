package hr.fer.progi.service;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;

import java.util.List;

public interface RatingService {

    /**
     * Returns all ratings where user is rated
     *
     * @param username
     * @return
     */
    List<Rating> userRatings(String username);

    /**
     * Returns all ratings where user has rated
     *
     * @param username
     * @return
     */
    List<Rating> authoredRatings(String username);

    Rating addRating(Rating rating);

}
