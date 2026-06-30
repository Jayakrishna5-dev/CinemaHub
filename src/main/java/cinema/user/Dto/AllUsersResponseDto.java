package cinema.user.Dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllUsersResponseDto {
	private String userName;
	private String email;
	private String Role;
	private LocalDateTime joinDateTime;
}
