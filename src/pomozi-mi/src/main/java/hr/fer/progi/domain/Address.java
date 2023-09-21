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
    private Double x_coord;

    @NotNull
    private Double y_coord;


    public Address(String description, Double x_coord, Double y_coord) {
        this.description = description;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    /**
     * Calculates distance between two longitude-latitude points
     * ergo address coordinates
     * @param a1 address1
     * @param a2 address2
     * @return distance between a1 and a2 in km
     */
    public static double calculateDistance(Address a1, Address a2){

        double p = Math.PI /180;
        double a = 0.5 - (Math.cos((a2.y_coord - a1.y_coord)*p)/2)
                + Math.cos(a1.y_coord*p) * Math.cos(a2.y_coord*p) * ((1-Math.cos((a1.x_coord- a2.x_coord)*p))/2);

        double ret = 12742 * Math.asin(Math.sqrt(a));
        System.out.println(ret);
        return ret;

    }

}
