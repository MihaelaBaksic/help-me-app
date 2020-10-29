package hr.fer.progi.service;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;

import java.util.List;

public interface RatingService {

    //returns all ratings where user is rated
    List<Rating> userRatings(String username);

    //returns all ratings where user has rated
    List<Rating> authoredRatings(String username);

    Rating addRating(Rating rating);

}
