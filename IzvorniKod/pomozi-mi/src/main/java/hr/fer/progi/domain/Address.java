package hr.fer.progi.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents address of registered user.
 */
@Data
@NoArgsConstructor
@Entity
public class Address {

    /**
     * Unique identifier for each address.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    @NotNull
    Double x_coord;

    @NotNull
    Double y_coord;


    public Address(String description, Double x_coord, Double y_coord) {
        this.description = description;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

}
