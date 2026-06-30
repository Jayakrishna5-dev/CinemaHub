package cinema.movie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequestDto {
	private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer durationMinutes;
    private String language;
    private String country;
    private BigDecimal imdbRating;
}
