package cinema.moviePerson.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAuthMoviePersonResponseDto {
	private long personId;
	private String name;
	private LocalDate birthDate;
	private String bio;
	private List<MovieListResponseDto> movies;
}
