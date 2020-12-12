package hr.fer.progi.mappers;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

/**
 * Represents data transfer object(DTO) for {@link Rating} entity.
 */
@Data
@AllArgsConstructor
public class RatingDTO {
    @NotNull
    private int rating;
    @NotNull
    private String comment;
    @NotNull
    private String ratedUsername;

    // TODO Id can be null
    private Long requestId;
}
