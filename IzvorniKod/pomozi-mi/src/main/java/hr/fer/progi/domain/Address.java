package hr.fer.progi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id")
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 50)
	private String streetName;
	
	@NotNull
	private int streetNumber;

	@NotNull
	private Long zipCode;

	@NotNull
	@Size(min = 2, max = 50)
	private String locationName;


	public Address(String streetName, int streetNumber, Long zipCode, String locationName){
		this.streetName=streetName;
		this.streetNumber=streetNumber;
		this.zipCode=zipCode;
		this.locationName=locationName;
	}

}
