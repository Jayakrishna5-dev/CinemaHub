package cinema.favorite.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequestDto {
    @NotNull(message = "Movie id is required")
    private long movieId;
}