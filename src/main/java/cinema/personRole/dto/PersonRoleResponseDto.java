package cinema.personRole.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRoleResponseDto {
	private String personRole;
	private long userId;
	private String addedBy;
}
