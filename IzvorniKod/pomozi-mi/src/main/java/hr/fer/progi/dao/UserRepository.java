package hr.fer.progi.dao;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    User findByUsername(String username);

    // TODO check this query. Does it need to have custom query, or can JPA automatically query it?
    @Query("SELECT r FROM Request r WHERE r.requestAuthor.username = :username")
    List<Request> findAllUserRequests(
            @Param("username") String username);

    int countByUsername(String username);

    boolean deleteUserByUsername(String username);
}
