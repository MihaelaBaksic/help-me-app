package hr.fer.progi.service;

import hr.fer.progi.dao.RatingRepository;
import hr.fer.progi.domain.Rating;
import hr.fer.progi.rest.RatingController;

import java.util.List;

/**
 * Represents connection between {@link RatingController} and {@link RatingRepository}.
 */
public interface RatingService {

    /**
     * Returns all ratings where user is rated.
     *
     * @param username
     * @return
     */
    List<Rating> userRatings(String username);

    /**
     * Returns all ratings where user has rated.
     *
     * @param username
     * @return
     */
    List<Rating> authoredRatings(String username);

    /**
     * Adds new rating.
     *
     * @param rating
     * @return Newly added rating
     */
    Rating addRating(Rating rating);

    /**
     * Calculates average rating for given User.
     *
     * @param username
     * @return average rating for given User
     */
    double calculateAverageRatingForUser(String username);

}
