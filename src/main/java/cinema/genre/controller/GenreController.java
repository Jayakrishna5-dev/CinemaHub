package cinema.genre.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.genre.dto.GenreRequestDto;
import cinema.genre.dto.GenreResponseDto;
import cinema.genre.service.GenreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class GenreController {
	private final GenreService service;
	
	@PostMapping("/genre")
	private GenreResponseDto addGenre(@RequestBody GenreRequestDto dto) {
		return service.saveGenre(dto);
	}
	
	@PatchMapping("/genre/{id}")
	private String updateGenre(@PathVariable long id, @RequestBody GenreRequestDto dto) {
		return service.updateGenre(id, dto);
	}
	
	@DeleteMapping("/genre/{id}")
	private String deleteGenre(@PathVariable long id) {
		return service.deleteGenre(id);
	}
}
