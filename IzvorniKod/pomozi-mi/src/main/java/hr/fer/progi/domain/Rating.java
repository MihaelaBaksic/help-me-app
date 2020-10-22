package hr.fer.progi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Rating {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private int rating;
	
	private String comment;
	
//	private User reviewer;
//	
//	private User rated;
//	
//	private Request request;
}
