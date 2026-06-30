package cinema.favorite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.favorite.entity.FavoriteEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

	boolean existsByMovieIdAndUserId(long movieId, Long id);

	Optional<List<FavoriteEntity>> findAllByUserId(Long id);

	long countByUserId(Long id);

}
