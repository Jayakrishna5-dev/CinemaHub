package cinema.role.roleEntity;


import cinema.common.auditVariables.AuditVariables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity extends AuditVariables {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

	@NotBlank(message = "Role name cannot be blank")
    @Column(nullable = false, unique = true)
    private String name;
}
