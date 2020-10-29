package hr.fer.progi.rest;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.RatingDTO;
import hr.fer.progi.mappers.UserDTO;
import hr.fer.progi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("") // ili user??
    @Secured("ROLE_USER")
    public List<Rating> getRatings(@RequestBody UserDTO userDTO){
        return ratingService.userRatings(userDTO.getUsername());
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    public ResponseEntity<Rating> placeRating(@RequestBody RatingDTO ratingDTO, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(ratingService.addRating(ratingDTO.mapToRating(user)));
    }
}
