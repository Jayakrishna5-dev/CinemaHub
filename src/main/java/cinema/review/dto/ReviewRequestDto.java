package cinema.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
	@NotNull(message = "Movie id is required")
    private long movieId;

    @NotBlank(message = "Review text is required")
    @Size(max = 2000, message = "Review text cannot exceed 2000 characters")
    private String reviewText;
}
