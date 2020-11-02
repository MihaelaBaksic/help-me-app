package hr.fer.progi.dao;

import hr.fer.progi.domain.Address;
import hr.fer.progi.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a.id FROM Address a WHERE a.streetName = :streetName AND a.streetNumber= :streetNumber AND a.location.zipCode= :zipCode" )
    Long getIdIfExists(
            @Param("streetName") String streetName, @Param("streetNumber") int streetNumber,
            @Param("zipCode") Long zipCode);

}
