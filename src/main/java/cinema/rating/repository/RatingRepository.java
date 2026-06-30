package cinema.rating.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.rating.entity.RatingEntity;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

	boolean existsByMovieIdAndUserId(Long movieId, Long id);

	Optional<List<RatingEntity>> findAllByUserId(Long id);

	long countByUserId(Long id);

}
