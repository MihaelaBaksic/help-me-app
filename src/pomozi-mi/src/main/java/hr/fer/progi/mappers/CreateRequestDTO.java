package hr.fer.progi.mappers;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.ref.ReferenceQueue;
import java.sql.Date;
import java.sql.Time;

/**
 * Represents data which user writes when he/she creates new request.
 */
@Data
@AllArgsConstructor
public class CreateRequestDTO {

    private Date expirationDate;
    private String title;
    private String description;
    private Address address;


    /**
     * Wraps {@link CreateRequestDTO} to {@link Request}.
     *
     * @param author
     * @return newly created request
     */
    public Request mapToRequest(User author) {
        Request newRequest = new Request();
        newRequest.setRequestStartTime(new Date(System.currentTimeMillis()));
        newRequest.setExpirationDate(this.expirationDate);
        newRequest.setTitle(this.title);
        newRequest.setDescription(this.description);
        newRequest.setRequestAuthor(author);
        newRequest.setAddress(this.address);
        newRequest.setStatus(RequestStatus.ACTNOANS);

        return newRequest;
    }

}
