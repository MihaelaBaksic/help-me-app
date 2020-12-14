package hr.fer.progi.mappers;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

/**
 * Represents data transfer object(DTO) for {@link Request} entity.
 */
@Data
@AllArgsConstructor
public class RequestDTO {

	private Long id;
    private Time duration;
    private String comment;
    private Address address;
    private RequestStatus status;
    private Set<User> potentialHandler;
    private User handler;
    
    
    public Request mapToRequest() {
    	Request r = new Request();
    	r.setId(id);
    	r.setAddress(address);
    	r.setComment(comment);
    	r.setDuration(duration);
    	r.setStatus(status);
    	r.setPotentialHandler(potentialHandler);
    	r.setRequestHandler(handler);
    	
    	return r;
    }

}
