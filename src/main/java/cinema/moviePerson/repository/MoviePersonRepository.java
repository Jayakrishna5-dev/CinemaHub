package cinema.moviePerson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.moviePerson.entity.MoviePersonEntity;

@Repository
public interface MoviePersonRepository extends JpaRepository<MoviePersonEntity, Long> {

	boolean existsByMovieIdAndPersonIdAndPersonRoleId(long movieId, long personId, long personRoleId);

	List<MoviePersonEntity> findAllByMovieId(long id);

	boolean existsByPersonId(long id);

	List<MoviePersonEntity> findByPersonId(long id);

}
