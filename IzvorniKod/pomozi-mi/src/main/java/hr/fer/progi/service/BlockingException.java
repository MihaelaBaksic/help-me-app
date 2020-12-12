package hr.fer.progi.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlockingException extends RuntimeException{
    public BlockingException(String message){
        super(message);
    }
}
