package hr.fer.progi.domain;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.sun.istack.NotNull;

import hr.fer.progi.mappers.RequestDTO;
import hr.fer.progi.service.exceptions.BlockingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request that needs to be handled.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {


    /**
     * Unique identifier for every request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Represents time when request has been created.
     */
    @NotNull
    private Date requestStartTime;

    /**
     * Represents time in which potential handler can respond to request. Default time is one week.
     */
    @NotNull
    private Date expirationDate;

    @NotNull
    @Column(length = 128)
    private String title;

    @NotNull
    @Column(length = 512)
    private String description;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private User requestAuthor;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private User requestHandler;

    /**
     * Represents all users who responded to request.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> potentialHandler;

    /**
     * Represents address of request author.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private Address address;

    /**
     * Represents state of request. Default is active but no answer(ACTNOANS).
     */
    @NotNull
    private RequestStatus status;

    /**
     * Creates request with specified duration, comment and address.
     *
     * @param expirationDate Date until potential handler can respond to request.
     * @param description  Text that gives more details for request
     * @param address  Address of request author
     */
    public Request(Date expirationDate, String title, String description, Address address) {
        this.expirationDate = expirationDate;
        this.title = title;
        this.description = description;
        this.address = address;
    }

    /**
     * Creates new {@link RequestDTO} from {@link Request}.
     *
     * @return new RequestDTO
     */
    public RequestDTO mapToRequestDTO() {
        System.out.println("MAPPING TO RequestDTO");
        return new RequestDTO(id, expirationDate, title, description, address, status, requestAuthor.mapToUserDTO(),
                potentialHandler!=null ? potentialHandler.stream().map(h -> h.mapToUserDTO()).collect(Collectors.toSet()) : null,
                requestHandler!=null ? requestHandler.mapToUserDTO() : null);
    }
    
    
    
    /**
     * Updates request data.
     * @param update request which carries updates.
     */
    public void updateRequest(Request update) {
    	if(this.status == RequestStatus.BLOCKED) {
    		throw new BlockingException("Request has been blocked by administrator! You cannot update it!");
    	}
    	if(update.description != null) this.setDescription(update.description);
    	if(update.expirationDate != null) this.setExpirationDate(update.expirationDate);
    	if(update.address != null) {
    		if(update.address.getLocationName() != null) this.address.setLocationName(update.address.getLocationName());
    		if(update.address.getStreetName() != null) this.address.setStreetName(update.address.getStreetName());
    		if(update.address.getStreetNumber() != null) this.address.setStreetNumber(update.address.getStreetNumber());
    		if(update.address.getZipCode() != null) this.address.setZipCode(update.address.getZipCode());
    	}
    	if(update.status != null) this.setStatus(update.status);
    }
    
}
