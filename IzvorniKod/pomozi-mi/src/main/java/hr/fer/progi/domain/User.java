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
	
	private Time blockTime;
}
