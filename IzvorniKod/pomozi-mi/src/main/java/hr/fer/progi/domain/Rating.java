package hr.fer.progi.domain;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents rating of handled request.
 */
@Entity
@Data
public class Rating {

    public Rating(int rating, String comment, User reviewer, User rated, Request request) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.rated = rated;
        this.request = request;
    }

    /**
     * Unique identifier for every rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Rate of handled request. Can be between 1 and 5.
     */
    @NotNull
    private int rating;

    private String comment;

    /**
     * Person that rates request handler.
     */
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User reviewer;

    /**
     * Person being rated.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private User rated;

    @OneToOne(cascade = CascadeType.ALL)
    private Request request;

}
