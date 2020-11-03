package hr.fer.progi.domain;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Rating {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private int rating;
	
	@NotNull
	private String comment;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.PERSIST)
	private User reviewer;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@NotNull
	private User rated;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Request request;
}
