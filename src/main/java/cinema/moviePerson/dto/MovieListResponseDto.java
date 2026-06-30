package cinema.moviePerson.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieListResponseDto {
	private long movieId;
	private String title;
	private LocalDate releaseDate;
	private List<String> roles;
}
