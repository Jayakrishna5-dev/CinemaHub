package cinema.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cinema.auth.config.JwtUtil;
import cinema.empInvitation.entity.EmpInvitationEntity;
import cinema.empInvitation.repository.EmpInvitationRepository;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.InvalidCredentialsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.role.repository.RoleRepository;
import cinema.role.roleEntity.RoleEntity;
import cinema.user.Dto.AllUsersResponseDto;
import cinema.user.Dto.ApiResponse;
import cinema.user.Dto.EmpProfileUpdateResponseDto;
import cinema.user.Dto.EmployeeRegisterDTO;
import cinema.user.Dto.EmployeeResponseDTO;
import cinema.user.Dto.LoginRequestDTO;
import cinema.user.Dto.LoginResponseDTO;
import cinema.user.Dto.UpdateUserProfileRequest;
import cinema.user.Dto.UserRegisterDTO;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final EmpInvitationRepository empinvRepo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public String register(UserRegisterDTO userdata) {
    	UserEntity user = new UserEntity();
    	
    	user.setName(userdata.getName());
    	user.setEmail(userdata.getEmail());
    	user.setPassword(encoder.encode(userdata.getPassword()));
    	RoleEntity roleEnt = roleRepo.findById((long) 2).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    	user.setRole(roleEnt);
    	user.setCreatedAt(LocalDateTime.now());
    	user.setCreatedBy(userdata.getName());
    	user.setUpdatedAt(LocalDateTime.now());
    	user.setUpdatedBy(userdata.getName());

        userRepo.save(user);
        
        return "User registered successfully";
    }

	public EmployeeResponseDTO registerEmp(EmployeeRegisterDTO empdto) {
    	EmpInvitationEntity emp = empinvRepo.findByInvitationCode(empdto.getInvitationCode()).orElseThrow(() -> new ResourceNotFoundException("Employee Invitation not found"));
    	if (!emp.getUsed()) {
    		UserEntity user = new UserEntity();
    		user.setName(empdto.getName());
        	user.setEmail(empdto.getEmail());
        	user.setPassword(encoder.encode(empdto.getPassword()));
    		RoleEntity role = emp.getRole();
    		user.setRole(role);
        	user.setCreatedAt(LocalDateTime.now());
        	user.setCreatedBy(empdto.getName());
        	user.setUpdatedAt(LocalDateTime.now());
        	user.setUpdatedBy(empdto.getName());
        	emp.setUsed(true);
        	empinvRepo.save(emp);
        	userRepo.save(user);
        	EmployeeResponseDTO res = new EmployeeResponseDTO();
        	res.setMessage("Registration successfull");
        	res.setRole(role.getName());
        	return res;
    	} else {
    		throw new AlreadyExistsException("Invitation code is already used");
    	}
	}
	
	public LoginResponseDTO processLogin(LoginRequestDTO dto) {
		Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		dto.getEmail(),
                		dto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
        	LoginResponseDTO res = new LoginResponseDTO();
        	res.setAccessToken(jwtUtil.generateToken(dto.getEmail()));
        	UserEntity user = userRepo.findByEmail(dto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        	res.setRole(user.getRole().getName());
        	res.setTokenType("Bearer");
        	res.setUserId(user.getId());
        	res.setUsername(user.getName());
        	return res;
        } else {
            throw new InvalidCredentialsException("Invalid credentials");
        }
	}

	public List<AllUsersResponseDto> getAllRegisteredUserDetails(long id) {
		RoleEntity roleEntity = roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
		List<UserEntity> userEntities = userRepo.findAllByRoleId(roleEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<AllUsersResponseDto> response = new ArrayList<>();
		for(UserEntity u : userEntities) {
			AllUsersResponseDto dto = new AllUsersResponseDto();
			dto.setEmail(u.getEmail());
			dto.setJoinDateTime(u.getCreatedAt());
			dto.setRole(u.getRole().getName());
			dto.setUserName(u.getName());
			response.add(dto);
		}
		return response;
	}

	public ApiResponse<EmpProfileUpdateResponseDto> updateProfile(long id, UpdateUserProfileRequest dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity mainUser = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		UserEntity user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		String message = "";
		if(dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
			message += "Email Updated ";
		}
		if(dto.getName() != null) {
			user.setCreatedBy(dto.getName());
			user.setName(dto.getName());
			message += "Name Updated ";
		}
		if(dto.getPassword() != null) {
			user.setPassword(encoder.encode(dto.getPassword()));
			message += "Password Updated";
		}
		if(!message.equals("")) {
			user.setUpdatedAt(LocalDateTime.now());
			user.setUpdatedBy(mainUser.getName());
			user.setId(user.getId());
			userRepo.save(user);
			
			EmpProfileUpdateResponseDto res = new EmpProfileUpdateResponseDto();
			res.setEmail(user.getEmail());
			res.setName(user.getName());
			ApiResponse<EmpProfileUpdateResponseDto> response = new ApiResponse<>();
		    response.setMessage(message);
		    response.setData(res);
		    return response;
		}
		
		throw new ResourceNotFoundException("Provide data to update profile");
	}

	public String deleteEmployee(long id) {
		UserEntity user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		userRepo.delete(user);
		return "Employee Deleted Successfully";
	}
}
