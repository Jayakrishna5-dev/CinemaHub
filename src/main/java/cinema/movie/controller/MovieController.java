package cinema.movie.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.movie.dto.ApiResponse;
import cinema.movie.dto.MovieRequestDto;
import cinema.movie.dto.MovieResponseDto;
import cinema.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class MovieController {
	private final MovieService service;
	
	@PostMapping("/movie")
	private MovieResponseDto addMovie(@Valid @RequestBody MovieRequestDto dto) {
		return service.saveMovie(dto);
	}
	
	@PatchMapping("/movie/{movieId}")
	private ApiResponse<MovieResponseDto> updateMovie(@PathVariable long movieId, @RequestBody MovieRequestDto dto) {
		return service.updateMovie(movieId, dto);
	}
	
	@DeleteMapping("/movie/{movieId}")
	private String deleteMovie(@PathVariable long movieId) {
		return service.deleteMovie(movieId);
	}
}
