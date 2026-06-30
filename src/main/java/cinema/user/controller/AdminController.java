package cinema.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.user.Dto.AllUsersResponseDto;
import cinema.user.Dto.ApiResponse;
import cinema.user.Dto.EmpProfileUpdateResponseDto;
import cinema.user.Dto.UpdateUserProfileRequest;
import cinema.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	private final UserService service;
	
	@GetMapping("/employee")
	public List<AllUsersResponseDto> getAllEmployeeDetails() {
		return service.getAllRegisteredUserDetails(3);
	}
	
	@GetMapping("/users")
	public List<AllUsersResponseDto> getAllUsersDetails() {
		return service.getAllRegisteredUserDetails(2);
	}
	
	@PatchMapping("/employee/{empId}")
	public ApiResponse<EmpProfileUpdateResponseDto> updateProfile(@PathVariable long empId, @RequestBody UpdateUserProfileRequest dto) {
		return service.updateProfile(empId, dto);
	}
	
	@DeleteMapping("employee/{empId}")
	public String deleteEmployee(@PathVariable long empId) {
		return service.deleteEmployee(empId);
	}
}
