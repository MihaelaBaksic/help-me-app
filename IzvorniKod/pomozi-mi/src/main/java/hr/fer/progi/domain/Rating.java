package hr.fer.progi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Rating {

	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private int rating;
	
	@NotNull
	private String comment;
	
	@NotNull
	@ManyToOne
	private User reviewer;
	
	@ManyToOne
	@NotNull
	private User rated;
	
	@OneToOne
	private Request request;
}
