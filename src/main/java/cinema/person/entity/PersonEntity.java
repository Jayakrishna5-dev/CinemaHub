package cinema.person.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cinema.common.auditVariables.AuditVariables;
import cinema.moviePerson.entity.MoviePersonEntity;
import cinema.user.userEntity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persons")
@Getter
@Setter
public class PersonEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Bio is required")
    @Size(max = 1000, message = "Bio cannot exceed 1000 characters")
    @Column(nullable = false, length = 1000)
    private String bio;

    @Past(message = "Birth date must be a past date")
    @Column(name = "birth_date")
    private LocalDate birthDate;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
	
	@OneToMany(
		mappedBy = "person",
    	cascade = CascadeType.ALL,
    	orphanRemoval = true
    )
    private List<MoviePersonEntity> moviePersons = new ArrayList<>();
}
