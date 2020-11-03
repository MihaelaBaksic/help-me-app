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
	@ManyToOne( cascade = CascadeType.ALL)
	@JoinColumn(name = "zip_code", referencedColumnName = "zip_code")
	private Location location;

	public Address(String streetName, int streetNumber, Location location){
		this.streetName=streetName;
		this.streetNumber=streetNumber;
		this.location=location;
	}

}
