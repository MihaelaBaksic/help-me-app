package hr.fer.progi.mappers;

import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import lombok.Getter;
import lombok.Setter;


public class UserDTO {

    @Getter @Setter
    private String username;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String surname;
    @Getter @Setter
    private String email;
    //profile picture
    @Getter @Setter
    private boolean administrator;

    public UserDTO(){
    }

    public UserDTO(String username, String name, String surname, String email, boolean administrator){
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.administrator = administrator;
    }



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
