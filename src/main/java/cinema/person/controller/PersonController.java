package cinema.person.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.person.dto.PersonRequestDto;
import cinema.person.dto.PersonResponseDto;
import cinema.person.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class PersonController {
	private final PersonService service;
	
	@PostMapping("/person")
	private PersonResponseDto addPerson(@Valid @RequestBody PersonRequestDto dto) {
		return service.savePerson(dto);
	}
	
	@PatchMapping("/person/{personId}")
	private String updatePerson(@PathVariable long personId, @RequestBody PersonRequestDto dto) {
		return service.updatePerson(personId, dto);
	}
	
	@DeleteMapping("/person/{personId}")
	private String deletePerson(@PathVariable long personId) {
		return service.deletePerson(personId);
	}
}
