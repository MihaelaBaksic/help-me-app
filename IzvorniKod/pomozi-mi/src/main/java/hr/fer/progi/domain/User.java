package hr.fer.progi.domain;

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
     * Creates new {@link UserDTO} from {@link User}.
     *
     * @return new UserDTO
     */
    public UserDTO mapToUserDTO() {
        return new UserDTO(username, firstName, lastName, email, administrator, status);
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
     */
    public User(String firstName, String lastName, String username, String password, String email,
                String phoneNumber, Address address, boolean admin, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.administrator = admin;
        this.status = status;
    }


    /**
     *
     * @param other    User object used to copy data to the referenced object
     *                 Fields administrator, status and states are not being copied
     *                 as they cannot be modified by the user
     */

    public void updateUserData(User other){
    	if(other.getFirstName() != null) firstName = other.getFirstName();
    	if(other.getLastName() != null) lastName = other.getLastName();
    	if(other.getUsername() != null) username = other.getUsername();
    	if(other.getPassword() != null) password = other.getPassword();
    	if(other.getEmail() != null) email = other.getEmail();
    	if(other.getPhoneNumber() != null) phoneNumber = other.getEmail();
    	if(other.getAddress().getLocationName() != null) address.setLocationName(other.getAddress().getLocationName());
   		if(other.getAddress().getStreetName() != null) address.setStreetName(other.getAddress().getStreetName());
   		if(other.getAddress().getStreetNumber() != null) address.setStreetNumber(other.getAddress().getStreetNumber());
    	if(other.getAddress().getZipCode() != null) address.setZipCode(other.getAddress().getZipCode());
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
    
    
    
    

}
