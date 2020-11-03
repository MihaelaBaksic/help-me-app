package hr.fer.progi.dao;

import hr.fer.progi.domain.Location;
import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * This is repository interface for Location entity.
 * By extending {@link JpaRepository} it contains the full API of
 * {@link CrudRepository} and {@link PagingAndSortingRepository}.
 * Methods in this repository are used for queries over database.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {



}
