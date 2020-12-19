package hr.fer.progi.dao;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.RequestStatus;
import hr.fer.progi.domain.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


/**
 * This is repository interface for {@link Request} entity. By extending {@link JpaRepository} it contains the full API
 * of {@link CrudRepository} and {@link PagingAndSortingRepository}. Methods in this repository are used for queries
 * over database.
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
	
	/**
	 * Sets request handler.
	 * @param id request id
	 * @param rhId request handler id
	 */
	@Modifying
	@Transactional
	@Query("update Request req set req.requestHandler = :requestHandle where req.id = :id")
	void updateRequestHandler(@Param("id") Long id, @Param("requestHandle") User rhId);

	
	/**
	 * Updated requests status.
	 * @param id request id
	 * @param status new request status
	 */
	@Modifying
	@Transactional
	@Query("update Request req set req.status = :status where req.id = :id")
	void updateRequestStatus(@Param("id") Long id, @Param("status") RequestStatus status);

}
