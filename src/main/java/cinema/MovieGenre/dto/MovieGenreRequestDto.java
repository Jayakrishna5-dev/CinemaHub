package cinema.MovieGenre.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieGenreRequestDto {
    @Positive(message = "Movie ID must be greater than 0")
	private long movieId;
	@Positive(message = "Genre ID must be greater than 0")
	private long genreId;
}
