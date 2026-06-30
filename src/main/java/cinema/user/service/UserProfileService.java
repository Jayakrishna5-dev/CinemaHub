package cinema.user.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cinema.Watchlist.repository.WatchlistRepository;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.favorite.repository.FavoriteRepository;
import cinema.rating.repository.RatingRepository;
import cinema.review.repository.ReviewRepository;
import cinema.user.Dto.ApiResponse;
import cinema.user.Dto.UpdateUserProfileRequest;
import cinema.user.Dto.UserProfileResponseDto;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {
	private final UserRepository userRepo;
	private final ReviewRepository reviewRepo;
	private final RatingRepository ratingRepo;
	private final FavoriteRepository favRepo;
	private final WatchlistRepository wRepo;
	private final PasswordEncoder encoder;

	public UserProfileResponseDto getuserProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return toDto(user, email);
	}

	private UserProfileResponseDto toDto(UserEntity user, String email) {
		long totalReviews = reviewRepo.countByUserId(user.getId());
		long totalRatings = ratingRepo.countByUserId(user.getId());
		long totalFavoriteMovies = favRepo.countByUserId(user.getId());
		long totalWatchlists = wRepo.countByUserId(user.getId());
		
		UserProfileResponseDto response = new UserProfileResponseDto();
		response.setJoinedOn(user.getCreatedAt());
		response.setTotalFavoriteMovies(totalFavoriteMovies);
		response.setTotalRatings(totalRatings);
		response.setTotalReviews(totalReviews);
		response.setTotalWatchlists(totalWatchlists);
		response.setUserEmail(email);
		response.setUserId(user.getId());
		response.setUserName(user.getName());
		return response;
	}

	public ApiResponse<UserProfileResponseDto> updateProfile(UpdateUserProfileRequest dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		String message = "";
		if(dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
			message += "Email Updated ";
		}
		if(dto.getName() != null) {
			user.setCreatedBy(dto.getName());
			user.setName(dto.getName());
			message += "Name Updated ";
		}
		if(dto.getPassword() != null) {
			user.setPassword(encoder.encode(dto.getPassword()));
			message += "Password Updated";
		}
		if(!message.equals("")) {
			user.setUpdatedAt(LocalDateTime.now());
			user.setUpdatedBy(user.getName());
			user.setId(user.getId());
			userRepo.save(user);
			
			UserProfileResponseDto profile = toDto(user, user.getEmail());
			ApiResponse<UserProfileResponseDto> response = new ApiResponse<>();
		    response.setMessage(message);
		    response.setData(profile);
		    return response;
		}
		
		throw new ResourceNotFoundException("Provide data to update profile");
		
	}
}
