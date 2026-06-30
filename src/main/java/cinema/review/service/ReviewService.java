package cinema.review.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.exception.customException.AccessDeniedException;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.review.dto.NoAuthReviewResponseDto;
import cinema.review.dto.ReviewRequestDto;
import cinema.review.dto.ReviewResponseDto;
import cinema.review.dto.UpdateReviewRequestDto;
import cinema.review.entity.ReviewEntity;
import cinema.review.repository.ReviewRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepo;
	private final UserRepository userRepo;
	private final MovieRepository movieRepo;

	public ReviewResponseDto saveReview(@Valid ReviewRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRole() == null) {
			throw new ResourceNotFoundException("Role not found");
		}
		if(!reviewRepo.existsByMovieIdAndUserId(dto.getMovieId(), user.getId())) {
			ReviewEntity entity = new ReviewEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			MovieEntity movie = movieRepo.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			entity.setMovie(movie);
			entity.setReviewText(dto.getReviewText());
			entity.setStatus("ACTIVE");
			reviewRepo.save(entity);
			
			return toDto(entity, user, movie);
			
		} else {
			throw new AlreadyExistsException("Review for this movie already exists");
		}
	}

	private ReviewResponseDto toDto(ReviewEntity entity, UserEntity user, MovieEntity movie) {
		ReviewResponseDto res = new ReviewResponseDto();
		res.setMovieName(entity.getMovie().getTitle());
		res.setUserId(entity.getUser().getId());
		res.setAddedBy(user.getName());
		res.setMovieId(movie.getId());
		res.setReviewText(entity.getReviewText());
		res.setStatus(entity.getStatus());
		res.setReviewId(entity.getId());
		return res;
	}

	@SuppressWarnings("unlikely-arg-type")
	public List<NoAuthReviewResponseDto> getReviews(long id) {
		List<ReviewEntity> res = reviewRepo.findAllByMovieId(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		List<NoAuthReviewResponseDto> result = new ArrayList<>();
		for(ReviewEntity r : res) {
			UserEntity user = r.getUser();
			NoAuthReviewResponseDto dto = new NoAuthReviewResponseDto();
			if(result.contains(dto.getUserName())) {
				continue;
			}
			dto.setUserName(user.getName());
			dto.setReviewId(r.getId());
			dto.setReviewText(r.getReviewText());
			dto.setStatus(r.getStatus());
			
			result.add(dto);
		}
		return result;
	}

	//This method fetches all Reviews without Pagination
//	public List<ReviewResponseDto> getAllReviews() {
//		String email = SecurityContextHolder.getContext().getAuthentication().getName();
//		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//		List<ReviewEntity> res = reviewRepo.findAllByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
//		List<ReviewResponseDto> response = new ArrayList<>();
//		for(ReviewEntity r : res) {
//			MovieEntity movie = movieRepo.findById(r.getMovie().getId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
//			response.add(toDto(r, user, movie));
//		}
//		return response;
//	}
	
	//This method fetches all Reviews with Pagination
	public Page<ReviewResponseDto> getAllReviews(int page, int size) {

	    String email = SecurityContextHolder.getContext().getAuthentication().getName();

	    UserEntity user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    Pageable pageable = PageRequest.of(page, size);

	    Page<ReviewEntity> reviews = reviewRepo.findAllByUserId(user.getId(), pageable);

	    return reviews.map(review -> {
	        MovieEntity movie = movieRepo.findById(review.getMovie().getId())
	                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

	        return toDto(review, user, movie);
	    });
	}

	public ReviewResponseDto updateReview(long id, UpdateReviewRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		ReviewEntity entity = reviewRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		if(!entity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this watchlist.");
		}
		MovieEntity movie = movieRepo.findById(entity.getMovie().getId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(user.getName());
		entity.setReviewText(dto.getReviewText());
		entity.setUser(user);
		reviewRepo.save(entity);
		return toDto(entity, user, movie);
	}

	public String deleteReview(long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		ReviewEntity entity = reviewRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
		if (!entity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this review.");
		}
		reviewRepo.delete(entity);

	    return "Review Deleted Successfully";
	}
}
