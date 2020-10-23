package hr.fer.progi.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Location {

	@Id
	private Long zipCode;
	
	private String locationName;
	
	
	
}
