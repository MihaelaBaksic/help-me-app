package hr.fer.progi.dao;

import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * This is repository interface for {@link Request} entity. By extending {@link JpaRepository} it contains the full API
 * of {@link CrudRepository} and {@link PagingAndSortingRepository}. Methods in this repository are used for queries
 * over database.
 */
public interface RequestRepository extends JpaRepository<Request, Long> {


}
