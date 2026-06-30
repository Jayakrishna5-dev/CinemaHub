package cinema.MovieGenre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.MovieGenre.entity.MovieGenreEntity;


@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Long> {

	boolean existsByMovieIdAndGenreId(long movieId, long genreId);
	List<MovieGenreEntity> findAllByMovieId(Long id);
	List<MovieGenreEntity> findByGenreId(Long id);

}
