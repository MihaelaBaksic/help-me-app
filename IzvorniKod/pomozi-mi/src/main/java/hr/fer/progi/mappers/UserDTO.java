package hr.fer.progi.mappers;

import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents data transfer object(DTO) for {@link User} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    //profile picture
    private boolean administrator;
    private UserStatus status;

    /**
     * Creates {@link User} from {@link UserDTO}.
     *
     * @return new User object
     */
    public User mapToUser() {

        User user = new User();
        user.setUsername(this.username);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setAdministrator(this.administrator);
        user.setStatus(this.status);
        return user;
    }

}
