package hr.fer.progi.mappers;

import java.sql.Date;
import java.sql.Time;
import lombok.Data;
import hr.fer.progi.domain.User;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.UserStatus;
@Data
public class RegistrationDTO {

	//registracija:

	//korisnik daje sljedeće informacije -> 
	//name, surname, username, email, password, dateOfBirth,
	//phoneNumber, profilePicture, address

	//ne daje -> id, administrator, status, blockTime

	@NotNull
	@Size(min = 2, max = 30)
	private String name;
	
	@NotNull
	@Size(min = 2, max = 30)
	private String surname;
	
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
	private Date dateOfBirth;
	
	@NotNull
	@Column(unique = true)
	private String phoneNumber;
	
	private boolean profilePicture;
	
	@ManyToOne
	private Address address;
	
	public RegistrationDTO(@Size(min = 2, max = 30) String name, @Size(min = 2, max = 30) String surname,
			@Size(min = 4, max = 20) String username, @Size(min = 6) String password, String email, Date dateOfBirth,
			String phoneNumber, boolean profilePicture, Address address) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.profilePicture = profilePicture;
		this.address = address;
	}
	
	public User mapToUser() {
		boolean admin = false;
		
		UserStatus status = UserStatus.NOTBLOCKED;
		
		Time blockedUntil = null;
		
		return new User(this.name, this.surname, 
		this.username, this.password, this.email, this.dateOfBirth,
		this.phoneNumber, this.profilePicture, this.address, 
		admin, status, blockedUntil);
	}
}
