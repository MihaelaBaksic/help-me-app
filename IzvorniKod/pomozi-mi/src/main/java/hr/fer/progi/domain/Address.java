package hr.fer.progi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Address {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 50)
	private String streetName;
	
	@NotNull
	private int streetNumber;
	
	@NotNull
	@ManyToOne
	private Location location;
	
}
