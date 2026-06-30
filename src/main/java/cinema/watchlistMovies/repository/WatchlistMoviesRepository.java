package cinema.watchlistMovies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.watchlistMovies.entity.WatchlistMoviesEntity;

@Repository
public interface WatchlistMoviesRepository extends JpaRepository<WatchlistMoviesEntity, Long> {

	boolean existsByWatchlistIdAndMovieIdAndUserId(Long watchlistId, Long movieId, Long id);

	Optional<List<WatchlistMoviesEntity>> findAllByUserIdAndWatchlistId(Long id, Long id2);

	int deleteByWatchlistIdAndMovieId(long watchlistId, long movieId);

}
