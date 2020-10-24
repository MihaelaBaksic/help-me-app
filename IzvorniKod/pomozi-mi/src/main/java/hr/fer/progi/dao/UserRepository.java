package hr.fer.progi.dao;

import hr.fer.progi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    int countByEmail(String email);

    User findByUsername(String username);

}
