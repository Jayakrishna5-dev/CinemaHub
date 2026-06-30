package cinema.person.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonResponseDto {
	private long personId;
	private String name;
	private String addedBy;
	private long userId;
}
