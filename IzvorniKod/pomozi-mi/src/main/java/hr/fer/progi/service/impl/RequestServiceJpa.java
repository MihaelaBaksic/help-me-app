package hr.fer.progi.service.impl;

import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;
import hr.fer.progi.domain.UserStatus;
import hr.fer.progi.service.*;

import hr.fer.progi.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
        Assert.notNull(request.getTitle(), "Title must be given");
        Assert.notNull(request.getDescription(), "Description must be given");
        Assert.notNull(request.getExpirationDate(), "Expiration date must be given");
        
        
        User requestAuthor = request.getRequestAuthor();
        if(requestAuthor == null) {
        	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        	requestAuthor = userRepository.findByUsername(username);
        	request.setRequestAuthor(requestAuthor);
        }
        if(!(requestAuthor.getStatus() == UserStatus.NOTBLOCKED)) {
        	throw new BlockingException("Your account is blocked!");
        }
        if(request.getAddress() != null)
            assertAddress(request);
        
        return requestRepository.save(request);
    }
    
    
    /**
     * Asserts address.
     * @param request
     */
    private void assertAddress(Request request) {
        Assert.notNull(request.getAddress().getDescription(), "Location description must be given");
        Assert.notNull(request.getAddress().getX_coord(), "X coordinate must be given");
        Assert.notNull(request.getAddress().getY_coord(), "Y coordinate must be given");
	}

	@Override
    public Request getRequestById(Long id) {
        Assert.notNull(id, "Request id must be given");

        // .getOne(id)  is changed to  .findById(id).get()  because the getOne() returns a reference
        return requestRepository.findById(id).get();
    }

    @Override
    public List<Request> findUserRequests(User user) {
        // TODO implement this (?)
        return null;
    }

    @Override
    public List<Request> findAuthoredRequests(User user) {
        Assert.notNull(user, "User must be given");

        return userRepository.findAllAuthoredRequests(user.getUsername());
    }

    @Override
    public List<Request> findHandlerRequests(User user) {
        Assert.notNull(user, "User must be given");

        return userRepository.findAllHandledRequests(user.getUsername());
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
        Assert.notNull(request.getTitle(), "Title must be given");
        Assert.notNull(request.getDescription(), "Description must be given");
        Assert.notNull(request.getExpirationDate(), "Expiration date must be given");
        assertAddress(request);
        

        if(request.getStatus() == RequestStatus.BLOCKED) {
        	throw new BlockingException("You cannot update blocked request!");
        }
        if(request.getStatus() == RequestStatus.ACCEPTED) {
        	throw new RequestAcceptedException("You cannot update request that has been accepted!");
        }
        if(request.getStatus() == RequestStatus.DONE) {
        	throw new RequestDoneException("You cannot update request that has already been processed!");
        }
        
        return requestRepository.save(request);
    }
    
    
    @Override
    public Request blockRequest(Request request) {
        if(request.getStatus() == RequestStatus.BLOCKED) {
        	throw new BlockingException("Request has already been blocked!");
        }
    	request.setStatus(RequestStatus.BLOCKED);
    	return requestRepository.save(request);
    }

    @Override
    public void deleteActiveAuthoredRequests(User user) {
        List<Request> activeAuthoredRequests = findAuthoredRequests(user)
                .stream()
                .filter(r -> r.getStatus() == RequestStatus.ACTNOANS || r.getStatus() == RequestStatus.ACTANS)
                .collect(Collectors.toList());

        requestRepository.deleteAll(activeAuthoredRequests);
    }


    @Override
    public Request requestRespond(Request request, User potentialHandler) {
        if(!(potentialHandler.getStatus() == UserStatus.NOTBLOCKED)) {
        	throw new BlockingException("Your account is blocked!");
        }
    	if(request.getRequestAuthor().getId() == potentialHandler.getId()) {
    		throw new RequestRespondException("You cannot respond to your own request!");
    	}
        if(request.getStatus() == RequestStatus.BLOCKED) {
        	throw new BlockingException("You cannot respond to blocked request!");
        }
        if(request.getStatus() == RequestStatus.ACCEPTED) {
        	throw new RequestAcceptedException("You cannot respond to request that has been accepted!");
        }
        if(request.getStatus() == RequestStatus.DONE) {
        	throw new  RequestDoneException("You cannot respond to request that has already been processed!");
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
    		throw new RequestAcceptedException("You already accepted someone for this request.");
    	}
    	if(user == null || user.getUsername() == null) {
    		throw new NonexistingObjectReferencedException("You must pick a user to handle request.");
    	}
    	if(request.getPotentialHandler() == null || request.getPotentialHandler().isEmpty()) {
    		throw new RequestHandlerException("No one has answered your request. Sorry :(");
    	}
    	if(request.getPotentialHandler().contains(user) == false) {
    		throw new RequestHandlerException("User " + user.getUsername() + " did not respond to this request.");
    	}
        if(!(user.getStatus() == UserStatus.NOTBLOCKED)) {
        	throw new BlockingException("That account is blocked!");
        }
        if(request.getStatus() == RequestStatus.BLOCKED) {
        	throw new BlockingException("You cannot pick handler for blocked request!");
        }
        if(request.getStatus() == RequestStatus.DONE) {
        	throw new  RequestDoneException("You cannot pick handler for request that has already been processed!");
        }
    	
    	
    	request.setRequestHandler(user);
    	request.setStatus(RequestStatus.ACCEPTED);
    	
    	requestRepository.updateRequestHandler(request.getId(), request.getRequestHandler());
    	requestRepository.updateRequestStatus(request.getId(), request.getStatus());
    	
    	return request;
    }

    @Override
    public Request denyRequestHandler(Request request, User u) {
        if (request.getStatus() != RequestStatus.ACTANS)
                throw new IllegalArgumentException("Request handler cannot be denied for request with status " + request.getStatus());

        if(! request.getPotentialHandler().contains(u))
            throw new IllegalArgumentException("User to be rejected isn't one of the potential handlers");

        request.setPotentialHandler(request.getPotentialHandler().stream().filter(h -> (! h.equals(u))).collect(Collectors.toSet()));
        if (request.getPotentialHandler().isEmpty())
            request.setStatus(RequestStatus.ACTNOANS);
        return requestRepository.save(request);
    }



    @Override
    public Request markRequestDone(Request request) {
        if(request.getStatus() == RequestStatus.DONE) {
        	throw new  RequestDoneException("You cannot mark done request that has already been processed!");
        }
        if(request.getStatus() == RequestStatus.BLOCKED) {
        	throw new BlockingException("You cannot mark done blocked request!");
        }
        
        request.setStatus(RequestStatus.DONE);
        requestRepository.updateRequestStatus(request.getId(), request.getStatus());
        return request;
    }

}
