package cinema.empInvitation.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;

import cinema.empInvitation.empInvitationDto.EmpInvitationRequestDto;
import cinema.empInvitation.empInvitationDto.EmpInvitationResponseDto;
import cinema.empInvitation.entity.EmpInvitationEntity;
import cinema.role.roleEntity.RoleEntity;

@Component
public class EmpInvitationMapper {
	public EmpInvitationEntity toEntity(EmpInvitationRequestDto reqdto, String code, RoleEntity role, String username) {
		EmpInvitationEntity entity = new EmpInvitationEntity();
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(username);
		entity.setEmail(reqdto.getEmail());
		entity.setExpiresAt(LocalDateTime.now().plusMinutes(10));
		entity.setInvitationCode(code);
		entity.setRole(role);
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(username);
		entity.setUsed(false);
		return entity;
	}
	
	public EmpInvitationResponseDto toResponse(EmpInvitationEntity entity) {
		EmpInvitationResponseDto response = new EmpInvitationResponseDto();
		LocalDateTime time = entity.getExpiresAt();
		String formattedTime = time.format(
				        DateTimeFormatter.ofPattern("dd MMM yyyy, HH : mm : ss", Locale.ENGLISH)
				).toLowerCase();
		response.setExpiresAt(formattedTime);
		response.setInvitationCode(entity.getInvitationCode());
		return response;
	}
}
