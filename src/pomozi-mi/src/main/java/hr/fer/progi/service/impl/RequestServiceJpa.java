package hr.fer.progi.service.impl;

import hr.fer.progi.dao.NotificationRepository;
import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.dao.UserRepository;
import hr.fer.progi.domain.*;
import hr.fer.progi.mappers.FilterDTO;
import hr.fer.progi.mappers.RequestDTO;
import hr.fer.progi.service.*;

import hr.fer.progi.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private NotificationRepository notificationRepository;

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
        
        Notification notification = new Notification(requestAuthor, "Vaš zahtjev je uspješno stvoren.", request, Notification.NotificationStatus.STANDARD);
        notificationRepository.save(notification);
        
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
    public List<Request> getFilteredRequests(FilterDTO filter) {
        Address currentUserAddress = userService.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName())
                .getAddress();
        List<Request> requests = this.listAll()
                .stream()
                .filter(r -> (r.getStatus() == RequestStatus.ACTNOANS || r.getStatus() == RequestStatus.ACTANS))
                .filter(r -> (r.getExpirationDate().after(new Date()) || r.getExpirationDate().equals(new Date())) )
                .filter(r -> (filter.getVirtual() && r.getAddress()==null) || (!filter.getVirtual() && (r.getAddress()==null ||
                        Address.calculateDistance(r.getAddress(), currentUserAddress) <= filter.getRadius())))
                .collect(Collectors.toList());

        //Address.calculateDistance(r.getAddress(), currentUserAddress) <= filter.getRadius())
        switch (filter.getOrder()){
            case ATOZ:
                requests.sort(Comparator.comparing(Request::getTitle));
                break;
            case ZTOA:
                requests.sort(Comparator.comparing(Request::getTitle).reversed());
                break;
        }

        return requests;
    }


    @Override
    public boolean deleteRequest(Long id) {
    	Request r = requestRepository.findById(id).get();

        
    	long beforeDeleting = requestRepository.count();
        System.out.println("ID za brisanje: " +  id);
        //Delete all notifications related to that request
        notificationService.updateToNullByRequestId(id);
    	requestRepository.delete(requestRepository.findById(id).get());
    	long afterDeleting = requestRepository.count();
    	
    	boolean isDeleted = beforeDeleting-1 == afterDeleting ? true : false;
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = userRepository.findByUsername(username);
    	
    	if(isDeleted && !user.isAdministrator()) {
    		Notification notification = new Notification(user, "Vaš zahtjev je uspješno izbrisan.", null, Notification.NotificationStatus.STANDARD);
    		notificationRepository.save(notification);
    	} else if(isDeleted && user.isAdministrator()) {
    		Notification notification = new Notification(user, "Vaš zahtjev je izbrisao administrator.", null, Notification.NotificationStatus.STANDARD);
    		notificationRepository.save(notification);
            if(r.getRequestHandler() == null && !r.getPotentialHandler().isEmpty()) {
            	for(User potentialHandler : r.getPotentialHandler()) {
            		Notification notifyPotentialHandler = new Notification(potentialHandler, "Zahtjev " + r.getTitle() + 
            				"je izbrisao administrator. Više se ne može izvršiti.", null, Notification.NotificationStatus.STANDARD);
            		notificationRepository.save(notifyPotentialHandler);
            	} 
            } else {
        		Notification notifyRequestHandler = new Notification(r.getRequestAuthor(), "Zahtjev " + r.getTitle() + 
        				"je izbrisao administrator. Više se ne može izvršiti.", null, Notification.NotificationStatus.STANDARD);
        		notificationRepository.save(notifyRequestHandler);
        	}
    		
    	}
    	
    	
    	return isDeleted;
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
        
        User user = userRepository.findByUsername(request.getRequestAuthor().getUsername());
        Notification notification = new Notification(user, "Vaš je zahtjev uspješno ažuriran.", request, Notification.NotificationStatus.STANDARD);
        notificationRepository.save(notification);
        
        return requestRepository.save(request);
    }
    
    
    @Override
    public Request blockRequest(Request request) {
        if(request.getStatus() == RequestStatus.BLOCKED) {
        	throw new BlockingException("Request has already been blocked!");
        }
    	request.setStatus(RequestStatus.BLOCKED);
    	
        User user = userRepository.findByUsername(request.getRequestAuthor().getUsername());
        Notification notification = new Notification(user, "Vaš je zahtjev uspješno blokiran.", request, Notification.NotificationStatus.STANDARD);
        notificationRepository.save(notification);
        
        if(request.getRequestHandler() == null && !request.getPotentialHandler().isEmpty()) {
        	for(User potentialHandler : request.getPotentialHandler()) {
        		Notification notifyPotentialHandler = new Notification(potentialHandler, "Zahtjev " + request.getTitle() + 
        				" je blokiran. Više se ne može izvršiti.", request, Notification.NotificationStatus.STANDARD);
        		notificationRepository.save(notifyPotentialHandler);
        	} 
        } else {
    		Notification notifyRequestHandler = new Notification(request.getRequestHandler(), "Zahtjev " + request.getTitle() + 
    				"je blokiran. Više se ne može izvršiti.", request, Notification.NotificationStatus.STANDARD);
    		notificationRepository.save(notifyRequestHandler);
    	}
    	
    	return requestRepository.save(request);
    }

    @Override
    public void deleteActiveAuthoredRequests(User user) {
        List<Request> activeAuthoredRequests = findAuthoredRequests(user)
                .stream()
                .filter(r -> r.getStatus() == RequestStatus.ACTNOANS || r.getStatus() == RequestStatus.ACTANS)
                .collect(Collectors.toList());
        
        List<Request> answeredRequests = findAuthoredRequests(user)
        		.stream()
        		.filter(r -> r.getStatus() == RequestStatus.ACTANS)
        		.collect(Collectors.toList());
        
        for(Request request : answeredRequests) {
        	for(User potentialHandler : request.getPotentialHandler()) {
        		Notification notification = new Notification(potentialHandler, "Zahtjev " + request.getTitle() + 
        				" nije moguće izvšiti jer je korisnik " + user.getUsername() + " blokiran", request, Notification.NotificationStatus.STANDARD);
        		notificationRepository.save(notification);
        	}
        }

        requestRepository.deleteAll(activeAuthoredRequests);
        

    }


    @Override
    public Request requestRespond(Request request, User potentialHandler) {
        if(!(potentialHandler.getStatus() == UserStatus.NOTBLOCKED)) {
        	throw new BlockingException("Your account is blocked!");
        }
    	if(request.getRequestAuthor().getId().equals( potentialHandler.getId())) {
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
        
    	User user = userRepository.findByUsername(request.getRequestAuthor().getUsername());
    	if(!request.getPotentialHandler().contains(potentialHandler)) {
	    	Notification notification = new Notification(user, "Korisnik " + potentialHandler.getUsername() + " se javio na vaš zahtjev", request, Notification.NotificationStatus.STANDARD);
	    	notificationRepository.save(notification);
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
    	
    	User requestAuthor = userRepository.findByUsername(request.getRequestAuthor().getUsername());
    	
    	Notification notifyRequestAuthor = new Notification(requestAuthor, "Odabrali ste korisnika " + user.getUsername() +
    			" za izvršavanje vašeg zahtjeva. Kontakt broj korisnika " + user.getUsername() + " je: " + 
    			user.getPhoneNumber() + ", te email adresa: " + user.getEmail(), request, Notification.NotificationStatus.STANDARD);
    	Notification notifyHandler = new Notification(user, "Odabrani ste za izvršavanje zahtjeva " + request.getTitle() +
    			". Kontakt broj autora zahtjeva je: " + requestAuthor.getPhoneNumber() +
    			", te email adresa: " + requestAuthor.getEmail(), request, Notification.NotificationStatus.STANDARD);
    	
    	notificationRepository.save(notifyHandler);
    	notificationRepository.save(notifyRequestAuthor);
    	
    	for(User deniedHandler : request.getPotentialHandler()) {
    		if(deniedHandler.equals(user)) {
    			continue;
    		}
    		Notification notifyRejectedHandlers = new Notification(deniedHandler, "Za zahtjev " + request.getTitle() + 
    				" je odabran izvršitelj, nažalost to niste Vi.", request, Notification.NotificationStatus.STANDARD);
    		notificationRepository.save(notifyRejectedHandlers);
    	}
    	
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
        
        
        Notification notifyRejectedHandler = new Notification(u, "Odbijeni ste za izvršavanje zahtjeva " + request.getTitle(), request, Notification.NotificationStatus.STANDARD);
        notificationRepository.save(notifyRejectedHandler);
        
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
        
        User requestHandler = request.getRequestHandler();
        Notification notifyRequestHandler = new Notification(requestHandler, "Zahtjev je uspješno izvšen."
        		+ " Molimo vas ocijenite autora zahtjeva.", request, Notification.NotificationStatus.NOTRATED);
        notificationRepository.save(notifyRequestHandler);

        User requestAuthor = request.getRequestAuthor();
        Notification notifyRequestAuthor = new Notification(requestAuthor, "Zahtjev je uspješno izvšen."
                + " Molimo vas ocijenite izvršitelja zahtjeva.", request, Notification.NotificationStatus.NOTRATED);
        notificationRepository.save(notifyRequestAuthor);
        
        return request;
    }

}
