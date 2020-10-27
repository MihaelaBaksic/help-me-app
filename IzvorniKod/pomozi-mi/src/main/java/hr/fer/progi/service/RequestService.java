package hr.fer.progi.service;

import hr.fer.progi.domain.Request;

import java.util.List;

public interface RequestService {

    /**
     * Lists all {@link Request} in the system.
     * @return All {@link Request} in the system
     */
    List<Request> listAll();

    /**
     * Adds given {@link Request} to the system.
     * @param request to be added in the system
     * @return added {@link Request}
     */
    Request addRequest(Request request);

    Request getRequestById(Long id);

}
