package cinema.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.user.Dto.ApiResponse;
import cinema.user.Dto.UpdateUserProfileRequest;
import cinema.user.Dto.UserProfileResponseDto;
import cinema.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {
	private final UserProfileService userProfileService;
	
	@GetMapping("/profile")
	public UserProfileResponseDto getUserProfile() {
		return userProfileService.getuserProfile();
	}
	
	@PatchMapping("/profile")
	public ApiResponse<UserProfileResponseDto> updateProfile(@RequestBody UpdateUserProfileRequest dto) {
		return userProfileService.updateProfile(dto);
	}
}
