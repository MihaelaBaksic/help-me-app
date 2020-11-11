package hr.fer.progi.dao;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This is repository interface for Address entity.
 * By extending {@link JpaRepository} it contains the full API of
 * {@link CrudRepository} and {@link PagingAndSortingRepository}.
 * Methods in this repository are used for queries over database.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

	/**
	 * Gets id if it exists in database.
	 * @param streetName
	 * @param streetNumber
	 * @param zipCode
	 * @return id
	 */
    @Query("SELECT a.id FROM Address a WHERE a.streetName = :streetName AND a.streetNumber= :streetNumber AND a.zipCode= :zipCode" )
    Long getIdIfExists(
            @Param("streetName") String streetName, @Param("streetNumber") int streetNumber,
            @Param("zipCode") Long zipCode);

}
