package hr.fer.progi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address {

	@Id
	@GeneratedValue
	private Long id;
	
	private String streetName;
	
	private int streetNumber;
	
	
	//private Location location;
	
}
