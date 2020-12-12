package hr.fer.progi.service.impl;

import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import hr.fer.progi.service.InvalidRequestException;
import hr.fer.progi.service.RequestService;
import hr.fer.progi.service.UnexistingUserReferencedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        Assert.notNull(request.getComment(), "Comment must be given");
        Assert.notNull(request.getDuration(), "Duration must be given");
        
        
        User requestAuthor = request.getRequestAuthor();
        if(requestAuthor == null) {
        	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        	requestAuthor = userRepository.findByUsername(username);
        	request.setRequestAuthor(requestAuthor);
        }
        if(request.getAddress() == null) {
        	request.setAddress(requestAuthor.getAddress());
        } else {
        	assertAddress(request);
        }
        
        return requestRepository.save(request);
    }
    
    
    /**
     * Asserts address.
     * @param request
     */
    private void assertAddress(Request request) {
        Assert.notNull(request.getAddress().getLocationName(), "Location name must be given");
        Assert.notNull(request.getAddress().getStreetName(), "Street name must be given");
        Assert.notNull(request.getAddress().getStreetNumber(), "Street number must be given");
        Assert.notNull(request.getAddress().getZipCode(), "Zip code must be given");		
	}

	@Override
    public Request getRequestById(Long id) {
        Assert.notNull(id, "Request id must be given");

        return requestRepository.getOne(id);
    }

    @Override
    public List<Request> findUserRequests(User user) {
        Assert.notNull(user, "User must be given");

        return userRepository.findAllUserRequests(user.getUsername());
    }
    
    
    @Override
    public boolean deleteRequest(Long id) {
    	long beforeDeleting = requestRepository.count();
    	requestRepository.deleteById(id);
    	long afterDeleting = requestRepository.count();
    	return beforeDeleting-1 == afterDeleting ? true : false;
    }
    
    
    
    @Override
    public Request updateRequest(Request request) {
        Assert.notNull(request, "Request must be given");
        Assert.notNull(request.getComment(), "Comment must be given");
        Assert.notNull(request.getDuration(), "Duration must be given");
        assertAddress(request);
        
        return requestRepository.save(request);
    }
    
    
    @Override
    public Request blockRequest(Request request) {
    	request.setStatus(RequestStatus.BLOCKED);
    	return requestRepository.save(request);
    }
    
    
    @Override
    public Request requestRespond(Request request, User potentialHandler) {
    	if(request.getRequestAuthor().getId() == potentialHandler.getId()) {
    		throw new InvalidRequestException("You cannot respond to your own request!"); // stvoriti novi exception 
    	}
    	if(request.getPotentialHandler() == null) {
    		Set<User> tmp = new HashSet<>();
    		tmp.add(potentialHandler);
    		request.setPotentialHandler(tmp);
    	} else {
    		request.getPotentialHandler().add(potentialHandler);
    	}
    	
    	request.setStatus(RequestStatus.ACTANS);
    	
    	return requestRepository.save(request);
    }
    
    
    @Override
    public Request pickRequestHandler(Request request, User user) {
    	if(request.getStatus() == RequestStatus.ACCEPTED) {
    		throw new InvalidRequestException("You already accepted someone for this request.");
    	}
    	if(user == null || user.getUsername() == null) {
    		throw new UnexistingUserReferencedException("You must pick a user to handle request.");
    	}
    	if(request.getPotentialHandler() == null || request.getPotentialHandler().isEmpty()) {
    		throw new InvalidRequestException("No one has answered your request."); // stvoriti novi exception
    	}
    	for(User u : request.getPotentialHandler()) {
    		System.out.println(u);
    	}
    	if(request.getPotentialHandler().contains(user) == false) {
    		throw new InvalidRequestException("User " + user.getUsername() + " did not respond to this request."); //stvoriti novi exception
    	}
    	
    	
    	request.setRequestHandler(user);
    	request.setStatus(RequestStatus.ACCEPTED);
    	
    	requestRepository.updateRequestHandler(request.getId(), request.getRequestHandler());
    	requestRepository.updateRequestStatus(request.getId(), request.getStatus());
    	
    	return request;
    }

}
