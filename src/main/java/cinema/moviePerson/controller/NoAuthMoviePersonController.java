package cinema.moviePerson.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.moviePerson.dto.NoAuthMoviePersonResponseDto;
import cinema.moviePerson.service.MoviePersonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class NoAuthMoviePersonController {
	private final MoviePersonService service;
	
	@GetMapping("/all")
	private List<String> getPersons() {
		return service.getPersons();
	}
	
	@GetMapping("{id}")
	private NoAuthMoviePersonResponseDto getPersonById(@PathVariable long id) {
		return service.getPersonById(id);
	}
	
	@GetMapping("/movie/{personName}")
	private NoAuthMoviePersonResponseDto getMovieDetailsFromPerson(@PathVariable String personName) {
		return service.getMovieDetailsFromPerson(personName);
	}
}
