package hr.fer.progi.mappers;

import java.sql.Time;
import hr.fer.progi.domain.Location;
import lombok.Data;
import hr.fer.progi.domain.User;
import javax.persistence.Column;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.UserStatus;

/**
 * Represents data which user writes when he/she
 * registers to web page.
 *
 */
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
	@Column(unique = true)
	private String phoneNumber;
	
	private String streetName;

	private int streetNo;

	private Long cityCode;

	private String cityName;
	
	public RegistrationDTO(@Size(min = 2, max = 30) String name, @Size(min = 2, max = 30) String surname,
			@Size(min = 4, max = 20) String username, @Size(min = 6) String password, String email,
			String phoneNumber, String streetName, int streetNo,
						   Long cityCode,String cityName ) {

		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.streetName = streetName;
		this.streetNo = streetNo;
		this.cityCode = cityCode;
		this.cityName = cityName;
	}
	
	/**
	 * Creates {@link User} from {@link RegistrationDTO}.
	 * @return new User
	 */
	public User mapToUser() {
		boolean admin = false;
		
		UserStatus status = UserStatus.NOTBLOCKED;
		
		Time blockedUntil = null;

		Location location = new Location(cityCode, cityName);
		Address address = new Address(this.streetName, this.streetNo, location);
		
		return new User(this.name, this.surname, 
		this.username, this.password, this.email,
		this.phoneNumber,  address,
		admin, status, blockedUntil);
	}
}
