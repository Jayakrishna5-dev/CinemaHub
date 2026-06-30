package cinema.personRole.entity;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "personroles")
public class PersonRoleEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_role_name", nullable = false, unique = true, length = 100)
    private String personRoleName;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @OneToMany(
    	mappedBy = "personRole",
   	    cascade = CascadeType.ALL,
   	    orphanRemoval = true
    )
    private List<MoviePersonEntity> moviePersons = new ArrayList<>();
}
