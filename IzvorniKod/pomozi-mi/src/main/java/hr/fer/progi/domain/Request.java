package hr.fer.progi.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Request {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private Date requestStartTime;
	
	private Time duration;
	
	private String comment;
	
//	private User requestAutor;
//	
//	private User requestHandler;
//	
//	private Address address;
}
