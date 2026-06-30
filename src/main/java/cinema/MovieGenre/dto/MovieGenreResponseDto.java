package cinema.MovieGenre.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieGenreResponseDto {
	private String movieTitle;
	private List<String> genres;
	private String addedBy;
}
