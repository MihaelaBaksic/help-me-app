package hr.fer.progi.mappers;

import hr.fer.progi.domain.Notification;
import hr.fer.progi.domain.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class NotificationDTO {

    @NotNull
    private UserDTO user;

    private RequestDTO request;

    @NotNull
    @Size(min=2, max = 100)
    private String message;

    @NotNull
    Boolean isRead;

    @NotNull
    private Notification.NotificationStatus status;

}
