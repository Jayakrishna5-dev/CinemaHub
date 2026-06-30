package cinema.Watchlist.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchlistResponseDto {
	private long userId;
	private String createdBy;
	private long watchlistId;
	private String watchlist;
}
