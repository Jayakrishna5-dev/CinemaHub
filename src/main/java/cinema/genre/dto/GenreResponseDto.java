package cinema.genre.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreResponseDto {
	private String genreName;
	private String message;
	private LocalDateTime createdAt;
}
