package cinema.rating.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResponseDto {
	private String MovieName;
	private double rating;
	private long userId;
	private String ratingAddedBy;
}
