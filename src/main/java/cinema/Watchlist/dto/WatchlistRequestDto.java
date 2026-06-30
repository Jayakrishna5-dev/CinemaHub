package cinema.Watchlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchlistRequestDto {
	@NotBlank(message = "Watchlist name is required")
    @Size(max = 100, message = "Watchlist name cannot exceed 100 characters")
    private String name;
}
