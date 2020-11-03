package hr.fer.progi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents address of registered user.
 */
@Data
@NoArgsConstructor
@Entity
public class Address {

	/**
	 * Unique identifier for each address.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id")
	private Long id;
	
	
	@NotNull
	@Size(min = 2, max = 50)
	private String streetName;
	
	@NotNull
	private int streetNumber;
	
	/**
	 * Represents user's place of residence.
	 */
	@NotNull
	@ManyToOne( cascade = CascadeType.ALL)
	@JoinColumn(name = "zip_code", referencedColumnName = "zip_code")
	private Location location;

	/**
	 * Creates address with specified street name, street number, and
	 * place of residence.
	 * @param streetName The street's name
	 * @param streetNumber The street's number
	 * @param location User's place of residence
	 */
	public Address(String streetName, int streetNumber, Location location){
		this.streetName=streetName;
		this.streetNumber=streetNumber;
		this.location=location;
	}

}
