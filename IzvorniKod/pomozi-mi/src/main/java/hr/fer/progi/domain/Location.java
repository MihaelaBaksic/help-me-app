package hr.fer.progi.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Location {

	@Id
	private Long zipCode;
	
	@NotNull
	@Size(min = 2, max = 50)
	private String locationName;
	
	
	
}
