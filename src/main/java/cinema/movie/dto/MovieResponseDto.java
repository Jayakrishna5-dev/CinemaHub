package cinema.movie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponseDto {
	private long id;
	private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer durationMinutes;
    private String language;
    private String country;
    private long userId;
    private String userName;
    private BigDecimal imdbRating;
}
