package hr.fer.progi.domain;

import java.sql.Time;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import hr.fer.progi.mappers.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

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
	
	@NotNull
	private boolean administrator;
	
	@NotNull
	private UserStatus status;
	
	private Time blockTime;

	public UserDTO mapToUserDTO() {
		return new UserDTO(username, firstName, lastName, email, administrator);
	}

	public User(String firstName, String lastName, String username, String password,
				String email, String phoneNumber,
				Address address, boolean admin, UserStatus status, Time blockedUntil){
		this.firstName=firstName;
		this.lastName=lastName;
		this.username=username;
		this.password=password;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.address=address;
		this.administrator=admin;
		this.status=status;
		this.blockTime = blockedUntil;

	}


}
