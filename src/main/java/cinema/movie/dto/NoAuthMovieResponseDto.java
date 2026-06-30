package cinema.movie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAuthMovieResponseDto {
	private long movieId;
	private String movieName;
	private String language;
	private BigDecimal imdbRating;
	private Integer durationMinutes;
	private String country;
	private LocalDate releaseDate;
	private String description;
	private List<String> movieGenres;
	private List<CastResponseDto> castAndCrew;
}