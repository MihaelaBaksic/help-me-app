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
    
    
    /**
     * Deletes request.
     * @param id request id
     * @return true if request has been successfully deleted, false otherwise.
     */
    boolean deleteRequest(Long id);
    
    
    /**
     * Updates request.
     * @param request Request which is being updated
     * @return newly updated request
     */
    Request updateRequest(Request request);
    
    
    /**
     * Blocks request.
     * @param request Request which is being blocked.
     * @return blocked request
     */
    Request blockRequest(Request request);
    
    
    /**
     * Allows user to respond to request.
     * @param request Request on which user responds
     * @param user User that responds to request
     * @return Request on which user respods
     */
    Request requestRespond(Request request, User user);
    
    
    
    /**
     * Allows user to pick request handler.
     * @param request Request for which request author pick handler
     * @param user request handler
     * @return Request for which request author pick handler
     */
    Request pickRequestHandler(Request request, User user);
    
    
    /**
     * Sets request status to DONE.
     * @param request
     * @return
     */
    Request markRequestDone(Request request);


}
