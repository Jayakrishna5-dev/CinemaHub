package cinema.watchlistMovies.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchlistMoviesRequestDto {
	@NotNull(message = "Watchlist id is required")
    private Long watchlistId;

    @NotNull(message = "Movie id is required")
    private Long movieId;
}
