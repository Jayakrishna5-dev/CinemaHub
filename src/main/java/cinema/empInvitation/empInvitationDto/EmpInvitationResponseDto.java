package cinema.empInvitation.empInvitationDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpInvitationResponseDto {
	private String invitationCode;
	private String expiresAt;
}
