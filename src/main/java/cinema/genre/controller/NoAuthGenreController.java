package cinema.genre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.genre.service.GenreService;
import cinema.movie.dto.NoAuthMovieResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
public class NoAuthGenreController {
	private final GenreService service;
	
	@GetMapping("/all")
	private List<String> getGenres() {
		return service.getGenres();
	}
	
	@GetMapping("/{genreName}")
	private List<String> getMoviesByGenre(@PathVariable String genreName) {
		return service.getMoviesByGenre(genreName);
	}
	
	@GetMapping("/movie/{movieName}")
	private NoAuthMovieResponseDto getMovieDetailsByGenre(@PathVariable String movieName) {
		return service.getMovieDetailsByGenre(movieName);
	}
}
