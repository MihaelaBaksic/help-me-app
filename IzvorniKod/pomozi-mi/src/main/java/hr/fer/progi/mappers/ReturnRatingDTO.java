package hr.fer.progi.mappers;

import hr.fer.progi.domain.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Represents data transfer object(DTO) for {@link Rating} entity when we return it to user.
 */
@Data
@AllArgsConstructor
public class ReturnRatingDTO {

    @NotNull
    private int rating;
    @NotNull
    private String comment;
    @NotNull
    private UserDTO reviewer;
    @NotNull
    private UserDTO ratedUser;

    private RequestDTO request;

}
