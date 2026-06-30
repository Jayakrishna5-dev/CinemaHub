package cinema.empInvitation.empInvitationDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpInvitationRequestDto {
	@Email
	private String email;
	@NotNull
	private Long roleId;
}
