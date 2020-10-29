package hr.fer.progi.service.impl;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceJpa implements RatingService {

    //TODO implement these methods
    @Override
    public List<Rating> userRatings(String username) {
        return null;
    }

    @Override
    public List<Rating> authoredRatings(String username) {
        return null;
    }

    @Override
    public Rating addRating(Rating rating) {
        return null;
    }
}
