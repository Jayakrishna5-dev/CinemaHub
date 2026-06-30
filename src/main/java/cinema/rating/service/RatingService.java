package cinema.rating.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.exception.customException.AccessDeniedException;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.rating.dto.RatingRequestDto;
import cinema.rating.dto.RatingResponseDto;
import cinema.rating.dto.UpdateRatingRequest;
import cinema.rating.entity.RatingEntity;
import cinema.rating.repository.RatingRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RatingService {
	private final RatingRepository ratingRepo;
	private final UserRepository userRepo;
	private final MovieRepository movieRepo;
	
	public RatingResponseDto saveRating(@Valid RatingRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRole() == null) {
			throw new ResourceNotFoundException("Role not found");
		}
		if(!ratingRepo.existsByMovieIdAndUserId(dto.getMovieId(), user.getId())) {
			RatingEntity entity = new RatingEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			MovieEntity movie = movieRepo.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			entity.setMovie(movie);
			entity.setRating(dto.getRating());
			ratingRepo.save(entity);
			
			return toDto(entity);
			
		} else {
			throw new AlreadyExistsException("Rating for this movie already exists");
		}
	}

	private RatingResponseDto toDto(RatingEntity entity) {
		RatingResponseDto res = new RatingResponseDto();
		res.setMovieName(entity.getMovie().getTitle());
		res.setRating(entity.getRating());
		res.setRatingAddedBy(entity.getUser().getName());
		res.setUserId(entity.getUser().getId());
		return res;
	}

	public List<RatingResponseDto> getAllRatings() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<RatingEntity> ratingEntities = ratingRepo.findAllByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<RatingResponseDto> response = new ArrayList<>();
		for(RatingEntity r : ratingEntities) {
			response.add(toDto(r));
		}
		return response;
	}

	public RatingResponseDto updateRating(long id, UpdateRatingRequest updateRatingDto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		RatingEntity ratingEntity = ratingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
		if(!ratingEntity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this watchlist.");
		}
		ratingEntity.setUpdatedAt(LocalDateTime.now());
		ratingEntity.setUpdatedBy(user.getName());
		ratingEntity.setRating(updateRatingDto.getRating());
		ratingEntity.setUser(user);
		ratingRepo.save(ratingEntity);
		return toDto(ratingEntity);
	}

	public String deleteRating(long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		RatingEntity entity = ratingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
		if (!entity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this rating.");
		}
		ratingRepo.delete(entity);

	    return "Rating Deleted Successfully";
	}

}
