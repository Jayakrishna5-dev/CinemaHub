package cinema.moviePerson.entity;

import cinema.common.auditVariables.AuditVariables;
import cinema.movie.entity.MovieEntity;
import cinema.person.entity.PersonEntity;
import cinema.personRole.entity.PersonRoleEntity;
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
@Table(
	name = "moviepersons",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"movie_id", "person_id", "person_role_id"}
		)
	}
)
@Getter
@Setter
public class MoviePersonEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull(message = "Movie ID is required")
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

	@NotNull(message = "Person ID is required")
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;
	
	@NotNull(message = "Person Role ID is required")
    @ManyToOne
    @JoinColumn(name = "person_role_id", nullable = false)
    private PersonRoleEntity personRole;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
