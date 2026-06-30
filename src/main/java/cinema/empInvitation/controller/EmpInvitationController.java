package cinema.empInvitation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.empInvitation.empInvitationDto.EmpInvitationRequestDto;
import cinema.empInvitation.empInvitationDto.EmpInvitationResponseDto;
import cinema.empInvitation.service.EmpInvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class EmpInvitationController {
	private final EmpInvitationService service;
	
	@PostMapping("/invitations")
	private EmpInvitationResponseDto addEmpInvitation(@Valid @RequestBody EmpInvitationRequestDto reqdto) {
		return service.saveEmpInvitation(reqdto);
	}
}
