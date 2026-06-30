package cinema.Watchlist.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.Watchlist.dto.RenameWatchlistDto;
import cinema.Watchlist.dto.WatchlistRequestDto;
import cinema.Watchlist.dto.WatchlistResponseDto;
import cinema.Watchlist.service.WatchlistService;
import cinema.movie.dto.NoAuthMovieResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class WatchlistController {
	private final WatchlistService service;
	
	@PostMapping("/watchlist")
	private WatchlistResponseDto addWatchlist(@Valid @RequestBody WatchlistRequestDto dto) {
		return service.saveWatchlist(dto);
	}
	
	@GetMapping("/watchlist")
	private List<String> getAllWatchlist() {
		return service.getAllWatchlist();
	}
	
	@GetMapping("/watchlist/{watchlistName}")
	private List<String> getWatchlistMovies(@PathVariable String watchlistName) {
		return service.getWatchlistMovies(watchlistName);
	}
	
	@GetMapping("/watchlist/movie/{movieName}")
	private NoAuthMovieResponseDto getWatchlistMovieDetails(@PathVariable String movieName) {
		return service.getWatchlistMovieDetails(movieName);
	}
	
	@DeleteMapping("/watchlists/{watchlistId}/movies/{movieId}")
	private String removeMovieFromWatchlist(@PathVariable long watchlistId, @PathVariable long movieId) {
		return service.removeMovieFromWatchlist(watchlistId, movieId);
	}
	
	@DeleteMapping("/watchlist/{watchlistId}")
	private String deleteWatchlist(@PathVariable long watchlistId) {
		return service.deleteWatchlist(watchlistId);
	}
	
	@PutMapping("/watchlist/{watchlistName}")
	private String renameWatchlist(@PathVariable String watchlistName, @RequestBody RenameWatchlistDto dto) {
		return service.renameWatchlist(watchlistName, dto);
	}
}
