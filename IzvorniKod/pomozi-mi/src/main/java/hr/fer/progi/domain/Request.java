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

/**
 * Represents a request that needs to be handled.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class
Request {


    /**
     * Unique identifier for every request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Time duration;

    @NotNull
    @Column(length = 512)
    private String comment;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private User requestAuthor;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private User requestHandler;

    /**
     * Represents all users who responded to request.
     */
    @OneToMany(cascade = CascadeType.ALL)
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
     * @param duration Time in which potential handler can respond to request.
     * @param comment  Text that gives more details for request
     * @param address  Address of request author
     */
    public Request(Time duration, String comment, Address address) {
        this.duration = duration;
        this.comment = comment;
        this.address = address;
    }

    /**
     * Creates new {@link RequestDTO} from {@link Request}.
     *
     * @return new RequestDTO
     */
    public RequestDTO mapToRequestDTO() {
        return new RequestDTO(id, duration, comment, address, requestAuthor);
    }
}
