package hr.fer.progi.rest;

import hr.fer.progi.service.BlockingException;
import hr.fer.progi.service.FailedLoginException;
import hr.fer.progi.service.InvalidCurrentUserException;
import hr.fer.progi.service.InvalidRequestException;
import hr.fer.progi.service.RequestAcceptedException;
import hr.fer.progi.service.RequestDoneException;
import hr.fer.progi.service.RequestHandlerException;
import hr.fer.progi.service.RequestRespondException;
import hr.fer.progi.service.UnexistingUserReferencedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgument(Exception e, WebRequest req) {
        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", "400");
        props.put("error", "Bad Request");
        return new ResponseEntity<>(props, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedLoginException.class)
    protected ResponseEntity<?> handleFailedLogin(Exception e, WebRequest req) {
        Map<String, String> props = new HashMap<>();
        props.put("message", e.getMessage());
        props.put("status", "400");
        props.put("error", "Bad Request");
        return new ResponseEntity<>(props, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnexistingUserReferencedException.class)
    protected ResponseEntity<?> handleUnexistingUserReferenced(Exception e, WebRequest req) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BlockingException.class)
    protected ResponseEntity<?> handleBlockingException(Exception e, WebRequest req) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(InvalidRequestException.class)
    protected ResponseEntity<?> handleInvalidRequestException(Exception e, WebRequest req) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(InvalidCurrentUserException.class)
    protected ResponseEntity<?> handleInvalidCurrentException(Exception e, WebRequest req) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(RequestAcceptedException.class)
    protected ResponseEntity<?> handleRequestAcceptedException(Exception e, WebRequest req) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(RequestDoneException.class)
    protected ResponseEntity<?> handleRequestDoneException(Exception e, WebRequest req) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(RequestHandlerException.class)
    protected ResponseEntity<?> handleRequestHandlerException(Exception e, WebRequest req) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(RequestRespondException.class)
    protected ResponseEntity<?> handleRequestRespondException(Exception e, WebRequest req) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    }

}
