package hr.fer.progi.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String surname;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private Date dateOfBirth;
	
	private String phoneNumber;
	
	private boolean profilePicture;
	
//	@OneToOne
//	private Address address;
	
	private boolean administrator;
}
