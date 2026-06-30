package cinema.moviePerson.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoviePersonRequestDto {
	@Positive(message = "Movie ID must be greater than 0")
	private long movieId;
	@Positive(message = "Person ID must be greater than 0")
	private long personId;
	@Positive(message = "Person Role ID must be greater than 0")
	private long personRoleId;
}
