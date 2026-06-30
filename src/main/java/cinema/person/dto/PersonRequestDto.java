package cinema.person.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequestDto {
	private String name;
	private String bio;
	private LocalDate birth_date;
}
