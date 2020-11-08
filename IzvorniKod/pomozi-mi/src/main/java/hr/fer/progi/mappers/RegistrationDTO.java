package hr.fer.progi.mappers;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import hr.fer.progi.domain.User;
import javax.persistence.Column;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.UserStatus;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationDTO {

	//registracija:

	//korisnik daje sljedeće informacije -> 
	//name, surname, username, email, password, dateOfBirth,
	//phoneNumber, profilePicture, address

	//ne daje -> id, administrator, status, blockTime

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
	@Column(unique = true)
	private String phoneNumber;
	
	private String streetName;

	private int streetNumber;

	private Long cityCode;

	private String cityName;
	
	public RegistrationDTO(@Size(min = 2, max = 30) String firstName, @Size(min = 2, max = 30) String lastName,
			@Size(min = 4, max = 20) String username, @Size(min = 6) String password, String email,
			String phoneNumber, String streetName, int streetNumber,
						   Long cityCode,String cityName ) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.cityCode = cityCode;
		this.cityName = cityName;
	}
	
	public User mapToUser() {
		boolean admin = false;
		
		UserStatus status = UserStatus.NOTBLOCKED;
		
		Time blockedUntil = null;

		Address address = new Address(this.streetName, this.streetNumber, this.cityCode, this.cityName);
		
		return new User(this.firstName, this.lastName,
		this.username, this.password, this.email,
		this.phoneNumber,  address,
		admin, status, blockedUntil);
	}
}
