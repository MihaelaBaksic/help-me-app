package hr.fer.progi.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Request {

	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private Date requestStartTime;
	
	@NotNull
	private Time duration;
	
	@NotNull
	@Column(length = 512)
	private String comment;
	
	@ManyToOne
	@NotNull
	private User requestAutor;
	
	@ManyToOne
	private User requestHandler;
	
	@OneToMany
	private Set<User> potentialHandler;
	
	@ManyToOne
	@NotNull
	private Address address;
	
	@NotNull
	private RequestStatus status;
}
