package cinema.user.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileRequest {
	private String name;
    private String email;
    private String password;
}
