package hr.fer.progi.dao;

import hr.fer.progi.domain.Location;
import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {



}
