package cinema.moviePerson.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.moviePerson.dto.MoviePersonRequestDto;
import cinema.moviePerson.service.MoviePersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class MoviePersonController {
	private final MoviePersonService service;
	
	@PostMapping("/movieperson")
	private String addMoviePerson(@Valid @RequestBody MoviePersonRequestDto dto) {
		return service.saveMoviePerson(dto);
	}
	
	@DeleteMapping("/movieperson/{moviePersonId}")
	private String deleteMoviePerson(@PathVariable long moviePersonId) {
		return service.deleteMoviePerson(moviePersonId);
	}
}
