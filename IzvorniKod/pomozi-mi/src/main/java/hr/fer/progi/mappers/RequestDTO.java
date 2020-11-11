package hr.fer.progi.mappers;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

/**
 * Represents data transfer object(DTO) for {@link Request} entity. 
 */
@Data
@AllArgsConstructor
public class RequestDTO {

    private Long id; // TODO is id needed
    private Time duration;
    private String comment;
    private Address address;
    private User requestAuthor;


}
