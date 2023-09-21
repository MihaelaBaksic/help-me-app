package hr.fer.progi.service.impl;

import hr.fer.progi.dao.RatingRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.exceptions.NonexistingUserReferencedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class RatingServiceJpaTest {

    @InjectMocks
    private RatingServiceJpa ratingServiceJpa; // System Under Test

    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void calculateAverageRatingForUser_noUserWithGivenUsernameThrowsNonexistingUserReferencedException() {
        String nonExsistingUsername = "nonExistingUsername";
        Mockito.when(userRepository.findByUsername(nonExsistingUsername)).thenReturn(null);

        assertThrows(NonexistingUserReferencedException.class, () -> ratingServiceJpa.calculateAverageRatingForUser(nonExsistingUsername));
    }

    @Test
    void calculateAverageRatingForUser_noRatingsWillResultInRatingZeroPointZero() {
        String username = "username";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(new User());
        Mockito.when(ratingRepository.findAllWhereUserIsRated(username)).thenReturn(Collections.emptyList());

        assertEquals(0.0, ratingServiceJpa.calculateAverageRatingForUser(username), 0.001);
    }

    @Test
    void calculateAverageRatingForUser_validCalculationOfAverageRating() {
        String username = "username";
        Rating rating1 = new Rating();
        rating1.setRating(3);
        Rating rating2 = new Rating();
        rating2.setRating(4);
        Rating rating3 = new Rating();
        rating3.setRating(5);
        Rating rating4 = new Rating();
        rating4.setRating(5);
        List<Rating> allRatings = Arrays.asList(rating1, rating2, rating3, rating4);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(new User());
        Mockito.when(ratingRepository.findAllWhereUserIsRated(username)).thenReturn(allRatings);

        double averageRating = ratingServiceJpa.calculateAverageRatingForUser(username);

        assertEquals(4.25, averageRating, 0.001);
    }
}