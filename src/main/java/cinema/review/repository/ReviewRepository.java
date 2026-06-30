package cinema.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.review.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

	boolean existsByMovieIdAndUserId(long movieId, Long id);

	Optional<List<ReviewEntity>> findAllByMovieId(long id);

	Optional<List<ReviewEntity>> findAllByUserId(Long id);

	long countByUserId(Long id);

	Page<ReviewEntity> findAllByUserId(Long id, Pageable pageable);

}
