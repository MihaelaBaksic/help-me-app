package hr.fer.progi.mappers;

import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;

public class UserDTO {

    private String username;
    private String name;
    private String surname;
    private String email;
    //profile picture
    private boolean administrator;

    public User mapToUser(){
        User user = new User();
        user.setUsername(this.username);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        user.setAdministrator(this.administrator);
        return user;
    }

}
