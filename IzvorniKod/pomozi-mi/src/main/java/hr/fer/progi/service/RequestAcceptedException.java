package hr.fer.progi.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestAcceptedException extends RuntimeException{

	public RequestAcceptedException(String message) {
		super(message);
	}
}
