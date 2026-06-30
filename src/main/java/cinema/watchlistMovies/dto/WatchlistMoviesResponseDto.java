package cinema.watchlistMovies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchlistMoviesResponseDto {
	private long watchlistId;
	private String watchlist;
	private long userId;
	private String addedBy;
	private String movie;
}
