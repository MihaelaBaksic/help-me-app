package hr.fer.progi.dao;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This is repository interface for User entity.
 * By extending {@link JpaRepository} it contains the full API of
 * {@link CrudRepository} and {@link PagingAndSortingRepository}.
 * Methods in this repository are used for queries over database.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Counts users by email. Should
	 * return 1 if there is user with given email, else
	 * 0. If this method returns number bigger than 1, there has been
	 * mistake in database.
	 * @param email
	 * @return 1 if there is user with given email, else 0
	 */
    int countByEmail(String email);

    /**
     * Finds user with given username
     * @param username
     * @return Found user or null if there is no such user
     * with given username
     */
    User findByUsername(String username);

    /**
     * Finds all request that one user has created.
     * @param username 
     * @return List of all request one user has created.
     */
    // TODO check this query. Does it need to have custom query, or can JPA automatically query it?
    @Query("SELECT r FROM Request r WHERE r.requestAuthor.username = :username")
    List<Request> findAllUserRequests(
            @Param("username") String username);

    /**
	 * Counts users by username. Should
	 * return 1 if there is user with given username, else
	 * 0. If this method returns number bigger than 1, there has been
	 * mistake in database.
     * @param username
     * @return 1 if there is user with given username, else 0
     */
    int countByUsername(String username);

    /**
     * Deletes user with given username from database.
     * @param username
     * @return true if user has been deleted successfully, false otherwise.
     */
    boolean deleteUserByUsername(String username);
}
