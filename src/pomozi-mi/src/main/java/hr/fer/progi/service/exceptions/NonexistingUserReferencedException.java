package hr.fer.progi.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NonexistingUserReferencedException extends RuntimeException {
    public NonexistingUserReferencedException(String message) {
        super(message);
    }
}