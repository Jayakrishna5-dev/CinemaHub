package cinema.genre.entity;

import java.util.ArrayList;
import java.util.List;

import cinema.MovieGenre.entity.MovieGenreEntity;
import cinema.common.auditVariables.AuditVariables;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class GenreEntity extends AuditVariables {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@Column(nullable = false, unique = true)
	private String genreName;
	@Column(nullable = false, unique = true)
	private String description;
	
	@OneToMany(
		mappedBy = "genre",
    	cascade = CascadeType.ALL,
    	orphanRemoval = true
    )
    private List<MovieGenreEntity> movieGenres = new ArrayList<>();
}
