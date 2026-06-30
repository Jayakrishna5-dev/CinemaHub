package cinema.user.Dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponseDto {
	private long userId;
	private String userName;
	private String userEmail;
	private LocalDateTime joinedOn;
	private long totalReviews;
	private long totalRatings;
	private long totalFavoriteMovies;
	private long totalWatchlists;
}
