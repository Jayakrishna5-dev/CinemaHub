package cinema.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.user.Dto.EmployeeRegisterDTO;
import cinema.user.Dto.EmployeeResponseDTO;
import cinema.user.Dto.LoginRequestDTO;
import cinema.user.Dto.LoginResponseDTO;
import cinema.user.Dto.UserRegisterDTO;
import cinema.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService service;
	
	@PostMapping("/register")
    public String register(@RequestBody UserRegisterDTO user) {
        return service.register(user);
    }
	
	@PostMapping("/employee/register")
    public EmployeeResponseDTO registerEmp(@RequestBody EmployeeRegisterDTO empdto) {
        return service.registerEmp(empdto);
    }
	
	
	@PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
		return service.processLogin(request);
    }
}
