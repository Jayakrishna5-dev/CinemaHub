package cinema.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
	private long reviewId;
	private long movieId;
	private String movieName;
	private String reviewText;
	private String status;
	private long userId;
	private String addedBy;
}
