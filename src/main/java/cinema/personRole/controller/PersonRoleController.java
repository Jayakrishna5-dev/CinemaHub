package cinema.personRole.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.personRole.dto.PersonRoleRequestDto;
import cinema.personRole.dto.PersonRoleResponseDto;
import cinema.personRole.service.PersonRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cms")
@RequiredArgsConstructor
public class PersonRoleController {
	private final PersonRoleService service;
	
	@PostMapping("/personrole")
	private PersonRoleResponseDto addPersonRole(@Valid @RequestBody PersonRoleRequestDto dto) {
		return service.savePersonRole(dto);
	}
	
	@PutMapping("/personrole/{personRoleId}")
	private String updatePersonRole(@PathVariable long personRoleId, @RequestBody PersonRoleRequestDto dto) {
		return service.updatePersonRole(personRoleId, dto);
	}
	
	@DeleteMapping("/personrole/{personRoleId}")
	private String deletePersonRole(@PathVariable long personRoleId) {
		return service.deletePersonRole(personRoleId);
	}
}
