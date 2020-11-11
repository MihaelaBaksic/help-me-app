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
    private String streetName;

    @NotNull
    private int streetNumber;

    /**
     * Represents user's place of residence.
     */
    @NotNull
    private Long zipCode;

    @NotNull
    @Size(min = 2, max = 50)
    private String locationName;

    /**
     * Creates address with specified street name, street number, and place of residence.
     *
     * @param streetName   The street's name
     * @param streetNumber The street's number
     * @param zipCode      zip code of User's place of residence
     * @param locationName User's place of residence
     */
    public Address(String streetName, int streetNumber, Long zipCode, String locationName) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.locationName = locationName;
    }

}
