package cinema.user.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
	private String accessToken;
	private String tokenType;
	private long userId;
	private String username;
	private String role;
}
