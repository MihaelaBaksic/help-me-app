package hr.fer.progi.domain;

import com.sun.istack.NotNull;
import hr.fer.progi.mappers.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Object representation of notification
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {

    public enum NotificationStatus {

        STANDARD,

        RATED,

        NOTRATED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @NotNull
    @Size(min = 2, max = 300)
    private String message;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Request request;

    @NotNull
    private Boolean isRead;

    @NotNull
    private NotificationStatus status;


    public Notification(User user, String message, Request request, NotificationStatus status){
        this.user = user;
        this.message = message;
        this.request = request;
        this.isRead = false;
        this.status = status;
    }

    public NotificationDTO mapToNotificationDTO(){
        return new NotificationDTO(this.user.mapToUserDTO(),
                this.request != null ? this.request.mapToRequestDTO() : null,
                this.message,
                this.isRead,
                this.status);
    }


}
