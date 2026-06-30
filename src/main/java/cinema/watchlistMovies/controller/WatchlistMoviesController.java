package cinema.watchlistMovies.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.watchlistMovies.dto.WatchlistMoviesRequestDto;
import cinema.watchlistMovies.dto.WatchlistMoviesResponseDto;
import cinema.watchlistMovies.service.WatchlistMoviesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class WatchlistMoviesController {
	private final WatchlistMoviesService service;
	
	@PostMapping("/watchlistmovies")
	private WatchlistMoviesResponseDto addWatchlistMovies(@Valid @RequestBody WatchlistMoviesRequestDto dto) {
		return service.saveWatchlistMovies(dto);
	}
}
