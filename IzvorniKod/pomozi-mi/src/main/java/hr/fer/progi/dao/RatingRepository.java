package hr.fer.progi.dao;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // TODO check if this query is optimized. I had 3 from databases :}
    @Query("SELECT r FROM Rating r WHERE r.rated.username = :username")
    List<Rating> findAllWhereUserIsRated(
            @Param("username") String username);

    @Query("SELECT r FROM Rating r WHERE r.reviewer.username = :username")
    List<Rating> findAllWhereUserHasRated(
            @Param("username") String username);

}
