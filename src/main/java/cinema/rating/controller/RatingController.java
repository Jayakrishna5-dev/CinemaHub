package cinema.rating.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.rating.dto.RatingRequestDto;
import cinema.rating.dto.RatingResponseDto;
import cinema.rating.dto.UpdateRatingRequest;
import cinema.rating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class RatingController {
	private final RatingService service;
	
	@PostMapping("/rating")
	private RatingResponseDto addRating(@Valid @RequestBody RatingRequestDto dto) {
		return service.saveRating(dto);
	}
	
	@GetMapping("/rating")
	private List<RatingResponseDto> getAllRatings() {
		return service.getAllRatings();
	}
	
	@PutMapping("/rating/{ratingId}")
	private RatingResponseDto updateRating(@PathVariable long ratingId, @RequestBody UpdateRatingRequest updateRatingDto) {
		return service.updateRating(ratingId, updateRatingDto);
	}
	
	@DeleteMapping("/rating/{ratingId}")
	private String deleteRating(@PathVariable long ratingId) {
		return service.deleteRating(ratingId);
	}
}
