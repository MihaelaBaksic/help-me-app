package hr.fer.progi.mappers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import hr.fer.progi.domain.Notification;
import lombok.Data;

@Data
public class CreateNotificationDTO {

    @NotNull
    private String username;

    private Long requestId;

    @NotNull
    @Size(min = 2, max = 100)
    private String message;

}
