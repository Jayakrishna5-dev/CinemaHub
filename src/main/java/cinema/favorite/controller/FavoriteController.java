package cinema.favorite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.favorite.dto.FavoriteRequestDto;
import cinema.favorite.dto.FavoriteResponseDto;
import cinema.favorite.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class FavoriteController {
	private final FavoriteService service;
	
	@PostMapping("/favorite")
	private FavoriteResponseDto addFavorite(@Valid @RequestBody FavoriteRequestDto dto) {
		return service.saveFavorite(dto);
	}
	
	@GetMapping("/favorite")
	private List<FavoriteResponseDto> getFavoriteMovies() {
		return service.getFavoriteMovies();
	}
	
	@DeleteMapping("/favorite/{favoriteId}")
	private String removeMovieFromFavorite(@PathVariable long favoriteId) {
		return service.removeMovieFromFavorite(favoriteId);
	}
}
