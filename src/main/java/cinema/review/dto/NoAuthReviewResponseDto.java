package cinema.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoAuthReviewResponseDto {
	private String userName;
	private long reviewId;
	private String reviewText;
	private String status;
}
