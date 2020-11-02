package hr.fer.progi.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import javax.persistence.*;

import com.sun.istack.NotNull;

import hr.fer.progi.mappers.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Date requestStartTime;
	
	@NotNull
	private Time duration;
	
	@NotNull
	@Column(length = 512)
	private String comment;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@NotNull
	private User requestAuthor;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private User requestHandler;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<User> potentialHandler;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@NotNull
	private Address address;
	
	@NotNull
	private RequestStatus status;

	public Request(Time duration, String comment, Address address){
		this.duration = duration;
		this.comment = comment;
		this.address = address;
	}

    public RequestDTO mapToRequestDTO() {
		return new RequestDTO(id, duration, comment, address, requestAuthor);
    }
}
