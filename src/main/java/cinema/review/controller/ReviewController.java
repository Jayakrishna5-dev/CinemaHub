package cinema.review.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cinema.review.dto.NoAuthReviewResponseDto;
import cinema.review.dto.ReviewRequestDto;
import cinema.review.dto.ReviewResponseDto;
import cinema.review.dto.UpdateReviewRequestDto;
import cinema.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService service;
	
	@PostMapping("/review")
	private ReviewResponseDto addReview(@Valid @RequestBody ReviewRequestDto dto) {
		return service.saveReview(dto);
	}
	
	@GetMapping("/reviews/movie/{movieId}")
	private List<NoAuthReviewResponseDto> getReviews(@PathVariable long movieId) {
		return service.getReviews(movieId);
	}
	
//	@GetMapping("/reviews")
//	private List<ReviewResponseDto> getAllReviews() {
//		return service.getAllReviews();
//	}
	
	@GetMapping("/reviews")
	public Page<ReviewResponseDto> getAllReviews(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    return service.getAllReviews(page, size);
	}
	
	@PutMapping("/review/{reviewId}")
	private ReviewResponseDto updateReview(@PathVariable long reviewId, @RequestBody UpdateReviewRequestDto dto) {
		return service.updateReview(reviewId, dto);
	}
	
	@DeleteMapping("/review/{reviewId}")
	private String deleteReview(@PathVariable long reviewId) {
		return service.deleteReview(reviewId);
	}
}
