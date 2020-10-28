package hr.fer.progi.dao;

import hr.fer.progi.domain.Rating;
import hr.fer.progi.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {



}
