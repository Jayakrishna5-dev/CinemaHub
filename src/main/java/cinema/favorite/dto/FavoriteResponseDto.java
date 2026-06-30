package cinema.favorite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteResponseDto {
	private long movieId;
	private String movie;
	private long userId;
	private String addedBy;
}
