package hr.fer.progi.service.impl;

import hr.fer.progi.dao.RatingRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of {@link RatingService} interface.
 */
@Service
public class RatingServiceJpa implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> userRatings(String username) {
        return ratingRepository.findAllWhereUserIsRated(username);
    }

    @Override
    public List<Rating> authoredRatings(String username) {
        return ratingRepository.findAllWhereUserHasRated(username);
    }

    @Override
    public Rating addRating(Rating rating) {
        Assert.notNull(rating, "Rating object must be given");

        return ratingRepository.save(rating);
    }

    @Override
    public double calculateAverageRatingForUser(String username) {
        return userRatings(username).stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElseThrow();
    }
}
