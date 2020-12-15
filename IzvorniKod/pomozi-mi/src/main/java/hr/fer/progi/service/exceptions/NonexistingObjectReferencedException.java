package hr.fer.progi.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NonexistingObjectReferencedException extends RuntimeException {
    public NonexistingObjectReferencedException(String message) {
        super(message);
    }
}
