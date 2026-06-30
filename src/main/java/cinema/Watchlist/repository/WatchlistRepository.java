package cinema.Watchlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.Watchlist.entity.WatchlistEntity;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistEntity, Long> {

	boolean existsByNameAndUserId(String name, Long id);

	Optional<List<WatchlistEntity>> findAllByUserId(Long id);

	Optional<WatchlistEntity> findByName(String watchlist);

	long countByUserId(Long id);

	Optional<WatchlistEntity> findByNameAndUserId(String watchlist, Long id);

}
