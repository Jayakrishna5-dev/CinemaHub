package cinema.empInvitation.entity;

import java.time.LocalDateTime;

import cinema.common.auditVariables.AuditVariables;
import cinema.role.roleEntity.RoleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EmployeeInvitation")
@Getter
@Setter
public class EmpInvitationEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message = "Email cannot be blank")
	@Email
	@Column(nullable = false)
	private String email;
	
	@NotNull(message = "Role id cannot be blank")
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;
	
	@NotBlank(message = "Invitation code cannot be blank")
	@Column(nullable = false, unique = true)
	private String invitationCode;
	
	@NotNull(message = "Must specify invitation code used or not with values true or false")
	@Column(nullable = false)
	private Boolean used;
	
	@NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    @Column(nullable = false)
	private LocalDateTime expiresAt;
	
}
