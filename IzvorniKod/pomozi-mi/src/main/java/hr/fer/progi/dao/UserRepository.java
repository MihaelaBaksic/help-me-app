package hr.fer.progi.dao;

import hr.fer.progi.domain.Request;
import hr.fer.progi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    User findByUsername(String username);

    @Query("")
    List<Request> findAllUserRequests(String email);

    int countByUsername(String username);
}
