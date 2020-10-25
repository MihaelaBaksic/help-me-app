package hr.fer.progi.service.impl;

import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RequestServiceJpa implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<Request> listAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request addRequest(Request request) {
        Assert.notNull(request, "Request must be given");
        return requestRepository.save(request);
    }


}
