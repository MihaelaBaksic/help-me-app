package hr.fer.progi.dao;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


/**
 * This is repository interface for {@link Address} entity. By extending {@link JpaRepository} it contains the full API
 * of {@link CrudRepository} and {@link PagingAndSortingRepository}. Methods in this repository are used for queries
 * over database.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Gets id if it exists in database.
     *
     * @param description
     * @param x_coord
     * @param y_coord
     * @return id
     */
    @Query("SELECT a.id FROM Address a WHERE a.description = :description AND a.x_coord= :x_coord AND a.y_coord= :y_coord")
    Long getIdIfExists(@Param("description") String description,
                       @Param("x_coord") Double x_coord,
                       @Param("y_coord") Double y_coord);

}
