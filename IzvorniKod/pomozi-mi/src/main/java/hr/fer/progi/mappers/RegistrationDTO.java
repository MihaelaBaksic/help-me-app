package hr.fer.progi.mappers;

import java.time.LocalDateTime;
import lombok.Data;
import hr.fer.progi.domain.User;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.UserStatus;

/**
 * Represents data which user writes when he/she registers to web page.
 */
@Data
public class RegistrationDTO {

    /*
    registracija:

    korisnik daje sljedeće informacije ->
    name, surname, username, email, password, dateOfBirth,
    phoneNumber, profilePicture, address

    ne daje -> id, administrator, status, blockTime
     */

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @Column(unique = true)
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String phoneNumber;

    private String description;

    private  Double x_coord;

    private  Double y_coord;



    public RegistrationDTO(@Size(min = 2, max = 30) String firstName, @Size(min = 2, max = 30) String lastName,
                           @Size(min = 4, max = 20) String username, @Size(min = 6) String password, String email,
                           String phoneNumber, String description, Double x_coord, Double y_coord) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    /**
     * Creates {@link User} from {@link RegistrationDTO}.
     *
     * @return new User
     */
    public User mapToUser() {

        boolean admin = false;
        UserStatus status = UserStatus.NOTBLOCKED;
        LocalDateTime blockedUntil = null;

        Address address = new Address(this.description, this.x_coord, this.y_coord);

        return new User(this.firstName, this.lastName,
                this.username, this.password, this.email,
                this.phoneNumber, address,
                admin, status);
    }
}
