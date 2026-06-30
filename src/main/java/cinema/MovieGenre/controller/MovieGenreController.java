package cinema.MovieGenre.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.MovieGenre.dto.MovieGenreRequestDto;
import cinema.MovieGenre.dto.MovieGenreResponseDto;
import cinema.MovieGenre.service.MovieGenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class MovieGenreController {
	private final MovieGenreService service;
	
	@PostMapping("/moviegenre")
	private MovieGenreResponseDto addMovieGenre(@Valid @RequestBody MovieGenreRequestDto dto) {
		return service.saveMovieGenre(dto);
	}
	
	@DeleteMapping("/moviegenre/{movieGenreId}")
	private String deleteMovieGenre(@PathVariable long movieGenreId) {
		return service.deleteMovieGenre(movieGenreId);
	}
}
