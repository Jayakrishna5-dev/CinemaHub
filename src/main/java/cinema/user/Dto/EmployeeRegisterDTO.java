package cinema.user.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRegisterDTO {
	private String name;
	private String email;
	private String password;
	private String invitationCode;
}