package hr.fer.progi.mappers;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import hr.fer.progi.wrappers.UserModelAssembler;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents data transfer object(DTO) for {@link Request} entity.
 */
@Data
@AllArgsConstructor
public class RequestDTO {


	private Long id;
    private Date expirationDate;
    private String title;
    private String description;
    private Address address;
    private RequestStatus status;
    private UserDTO requestAuthor;
    private Set<UserDTO> potentialHandler;
    private UserDTO handler;
    
    
    public Request mapToRequest() {
    	Request r = new Request();
    	r.setId(id);
    	r.setAddress(address);
    	r.setTitle(title);
    	r.setDescription(description);
    	r.setExpirationDate(expirationDate);
    	r.setStatus(status);
		r.setRequestAuthor(requestAuthor.mapToUser());
    	r.setPotentialHandler(potentialHandler.stream().map( re -> re.mapToUser()).collect(Collectors.toSet()));
    	r.setRequestHandler(handler.mapToUser());

    	return r;
    }

}
