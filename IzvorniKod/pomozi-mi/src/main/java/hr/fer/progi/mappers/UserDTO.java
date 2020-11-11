package hr.fer.progi.mappers;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents data transfer object(DTO) for {@link User} entity. 
 */
@Data
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    //profile picture
    private boolean administrator;

    public UserDTO(){
    }

    public UserDTO(String username, String firstName, String lastName, String email, boolean administrator){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.administrator = administrator;
    }

    /**
     * Creates {@link User} from {@link UserDTO}.
     * @return new User
     */
    public User mapToUser(){
        User user = new User();
        user.setUsername(this.username);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setAdministrator(this.administrator);
        return user;
    }

}
