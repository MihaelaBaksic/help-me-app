package hr.fer.progi.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	
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
	
	@NotNull
	private boolean administrator;
	
	@NotNull
	private UserStatus status;
	
	private Time blockedUntil;

	public User(@Size(min = 2, max = 30) String name, @Size(min = 2, max = 30) String surname,
			@Size(min = 4, max = 20) String username, @Size(min = 6) String password, String email, Date dateOfBirth,
			String phoneNumber, boolean profilePicture, Address address, boolean administrator, UserStatus status,
			Time blockedUntil) {
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
		this.administrator = administrator;
		this.status = status;
		this.blockedUntil = blockedUntil;
	}
	
}
