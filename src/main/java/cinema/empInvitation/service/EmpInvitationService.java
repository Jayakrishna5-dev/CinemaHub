package cinema.empInvitation.service;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.empInvitation.empInvitationDto.EmpInvitationRequestDto;
import cinema.empInvitation.empInvitationDto.EmpInvitationResponseDto;
import cinema.empInvitation.entity.EmpInvitationEntity;
import cinema.empInvitation.mapper.EmpInvitationMapper;
import cinema.empInvitation.repository.EmpInvitationRepository;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.role.repository.RoleRepository;
import cinema.role.roleEntity.RoleEntity;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class EmpInvitationService {
	private final EmpInvitationRepository empinvrepo;
	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	
	public EmpInvitationResponseDto saveEmpInvitation(@Valid EmpInvitationRequestDto reqdto) {
		
		if(empinvrepo.findByEmail(reqdto.getEmail()).isEmpty()) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
			String invitationCode = UUID.randomUUID().toString();
			RoleEntity actualRole = roleRepo.findById(reqdto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role not found"));

			EmpInvitationMapper mapper = new EmpInvitationMapper();
			EmpInvitationEntity entity = mapper.toEntity(reqdto, invitationCode, actualRole, user.getName());
			empinvrepo.save(entity);
			return mapper.toResponse(entity);
		} else {
			throw new AlreadyExistsException("Email already exists");
		}
	}

}
