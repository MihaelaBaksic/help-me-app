package hr.fer.progi.domain;

import java.sql.Time;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import hr.fer.progi.mappers.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents one user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    /**
     * Unique identifier for every user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private boolean profilePicture;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    /**
     * Tells whether user is administrator or not. Default is not an administrator.
     */
    @NotNull
    private boolean administrator;

    /**
     * Represents status of users account. Default is NOTBLOCKED.
     */
    @NotNull
    private UserStatus status;


    /**
     * Represents time period in which user is blocked.
     */
    private Time blockTime;


    /**
     * Creates new {@link UserDTO} from {@link User}.
     *
     * @return new UserDTO
     */
    public UserDTO mapToUserDTO() {
        return new UserDTO(username, firstName, lastName, email, administrator);
    }

    /**
     * Creates one user.
     *
     * @param firstName    User's name
     * @param lastName     User's surname
     * @param username     User's username
     * @param password     User's password
     * @param email        User's email
     * @param phoneNumber  User's phone number
     * @param address      User's address
     * @param admin        Is user admin or not
     * @param status       User's account status
     * @param blockedUntil Time period in which user is blocked
     */
    public User(String firstName, String lastName, String username, String password, String email,
                String phoneNumber, Address address, boolean admin, UserStatus status, Time blockedUntil) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.administrator = admin;
        this.status = status;
        this.blockTime = blockedUntil;
    }

}
