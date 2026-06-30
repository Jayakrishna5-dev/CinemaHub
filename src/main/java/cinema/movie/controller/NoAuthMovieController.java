package cinema.movie.controller;


import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cinema.movie.dto.NoAuthMovieResponseDto;
import cinema.movie.service.MovieService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class NoAuthMovieController {
	private final MovieService service;
	
	@GetMapping("/{movieId}")
	private NoAuthMovieResponseDto getMovieById(@PathVariable long movieId) {
		return service.getMovieById(movieId);
	}
	
//	@GetMapping("/all")
//	private List<NoAuthMovieResponseDto> getMovies() {
//		return service.getMovies();
//	}
	
	@GetMapping("/all")
	public Page<NoAuthMovieResponseDto> getMovies(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {

	    return service.getMovies(page, size);
	}
}
