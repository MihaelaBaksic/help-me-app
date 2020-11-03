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

    private Time duration;
    private String comment;
    private Address address;

    
    /**
     * Wraps {@link CreateRequestDTO} to {@link Request}.
     * @param author
     * @return newly created request
     */
    //TODO add all args constructor to Request and fix this or constructor accepting these args
    public  Request mapToRequest(User author){
        Request newRequest = new Request();
        newRequest.setRequestStartTime( new Date(System.currentTimeMillis()));
        newRequest.setDuration(this.duration);
        newRequest.setComment(this.comment);
        newRequest.setRequestAuthor(author);
        newRequest.setAddress(this.address);
        newRequest.setStatus(RequestStatus.ACTNOANS);

        return newRequest;
    }

}
