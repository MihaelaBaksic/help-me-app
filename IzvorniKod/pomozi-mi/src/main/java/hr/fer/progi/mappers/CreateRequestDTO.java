package hr.fer.progi.mappers;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;

import java.lang.ref.ReferenceQueue;
import java.sql.Date;
import java.sql.Time;

public class CreateRequestDTO {

    private Time duration;
    private String comment;
    private Address address;

    public Time getDuration(){ return duration;}

    public String getComment(){ return comment;}

    public Address getAddress() { return address;}


    //TODO add all args constructor to Request and fix this or constructor accepting these args
    public  Request mapToRequest(User author){
        Request newRequest = new Request();
        newRequest.setRequestStartTime( new Date(System.currentTimeMillis()));
        newRequest.setDuration(this.duration);
        newRequest.setComment(this.comment);
        newRequest.setRequestAutor(author);
        newRequest.setAddress(this.address);
        newRequest.setStatus(RequestStatus.ACTNOANS);

        return newRequest;
    }


}
