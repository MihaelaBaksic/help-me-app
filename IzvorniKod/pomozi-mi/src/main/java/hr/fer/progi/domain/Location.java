package hr.fer.progi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

	@Id
	@Column(name="zip_code")
	private Long zipCode;
	
	@NotNull
	@Size(min = 2, max = 50)
	private String locationName;

}
