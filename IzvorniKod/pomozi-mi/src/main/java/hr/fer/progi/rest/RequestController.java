package hr.fer.progi.rest;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.mappers.CreateRequestDTO;
import hr.fer.progi.mappers.CreateRequestDTO;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.impl.RequestServiceJpa;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.*;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;


    @GetMapping("")
    public List<Request> listRequests() {
        return requestService.listAll();
    }

    @PostMapping("")
    public ResponseEntity<Request> createRequest(@RequestBody CreateRequestDTO createRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(requestService.addRequest(createRequest.mapToRequest(user)));
    }

}
