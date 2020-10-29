package hr.fer.progi.mappers;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class RatingDTO {
    @NotNull
    private int rating;
    @NotNull
    private String comment;
    @NotNull
    private User rated;
    private Request request;

    public Rating mapToRating(User reviewer){
        Rating rating = new Rating();
        rating.setRating(this.rating);
        rating.setComment(this.comment);
        rating.setReviewer(reviewer);
        rating.setRated(this.rated);
        rating.setRequest(this.request);
        return rating;
    }
}
