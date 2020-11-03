package hr.fer.progi.service.impl;

import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.RequestService;
import net.bytebuddy.implementation.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


/**
 * Implementation of {@link RequestService} interface.
 */
@Service
public class RequestServiceJpa implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Request> listAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request addRequest(Request request) {
        Assert.notNull(request, "Request must be given");
        return requestRepository.save(request);
    }

    @Override
    public Request getRequestById(Long id){
        Assert.notNull(id, "Request id must be given");
        return requestRepository.getOne(id);
    }

    @Override
    public List<Request> findUserRequests(User user) {
        Assert.notNull(user, "User must be given");
        return userRepository.findAllUserRequests(user.getUsername());
    }

}
