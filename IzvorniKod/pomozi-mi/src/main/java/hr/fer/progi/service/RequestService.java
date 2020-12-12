package hr.fer.progi.service;

import hr.fer.progi.dao.RequestRepository;
import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import hr.fer.progi.rest.RequestController;

import java.util.List;

/**
 * Represents connection between {@link RequestController} and {@link RequestRepository}.
 */
public interface RequestService {

    /**
     * Lists all {@link Request} in the system.
     *
     * @return All {@link Request} in the system
     */
    List<Request> listAll();

    /**
     * Adds given {@link Request} to the system.
     *
     * @param request to be added in the system
     * @return added {@link Request}
     */
    Request addRequest(Request request);

    /**
     * Finds request for given id.
     *
     * @param id
     * @return Founded request or null if request is not existing
     */
    Request getRequestById(Long id);

    /**
     * Finds all {@link Request} from given {@link User}.
     *
     * @param user which {@link Request} we should find
     * @return all {@link Request} that given {@link User} has
     */
    List<Request> findUserRequests(User user);
    
    
    boolean deleteRequest(Long id);
    
    
    Request updateRequest(Request request);
    
    
    Request blockRequest(Request request);
    
    
    Request requestRespond(Request request, User user);
    
    
    Request pickRequestHandler(Request request, User user);


}
