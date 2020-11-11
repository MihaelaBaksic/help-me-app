package hr.fer.progi.dao;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * This is repository interface for {@link Rating} entity. By extending {@link JpaRepository} it contains the full API
 * of {@link CrudRepository} and {@link PagingAndSortingRepository}. Methods in this repository are used for queries
 * over database.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Gets all ratings where user has been rated.
     *
     * @param username Rated user's username
     * @return List of all found ratings
     */
    @Query("SELECT r FROM Rating r WHERE r.rated.username = :username")
    List<Rating> findAllWhereUserIsRated(
            @Param("username") String username);

    /**
     * Gets all ratings where given user rated other user.
     *
     * @param username Username of user who rated others
     * @return List of all ratings user gave
     */
    @Query("SELECT r FROM Rating r WHERE r.reviewer.username = :username")
    List<Rating> findAllWhereUserHasRated(
            @Param("username") String username);

}
