package cinema.MovieGenre.entity;

import cinema.common.auditVariables.AuditVariables;
import cinema.genre.entity.GenreEntity;
import cinema.movie.entity.MovieEntity;
import cinema.user.userEntity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
	name = "moviegenres",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"movie_id", "genre_id"}
		)
	}
)

public class MovieGenreEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull(message = "Movie ID is required")
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

	@NotNull(message = "Genre ID is required")
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genre;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
