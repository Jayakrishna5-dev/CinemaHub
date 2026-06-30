package cinema.personRole.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.personRole.dto.PersonRoleRequestDto;
import cinema.personRole.dto.PersonRoleResponseDto;
import cinema.personRole.entity.PersonRoleEntity;
import cinema.personRole.repository.PersonRoleRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class PersonRoleService {
	private final PersonRoleRepository personRoleRepo;
	private final UserRepository userRepo;
	
	public PersonRoleResponseDto savePersonRole(@Valid PersonRoleRequestDto dto) {
		if(!personRoleRepo.existsByPersonRoleName(dto.getPersonRoleName())) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
			if(user.getRole() == null) {
				throw new ResourceNotFoundException("Role not found");
			}

			PersonRoleEntity entity = new PersonRoleEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setPersonRoleName(dto.getPersonRoleName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			personRoleRepo.save(entity);
			
			PersonRoleResponseDto res = new PersonRoleResponseDto();
			res.setAddedBy(entity.getUser().getName());
			res.setPersonRole(entity.getPersonRoleName());
			res.setUserId(entity.getUser().getId());
			return res;
			
		} else {
			throw new AlreadyExistsException("Person Role already exists");
		}
	}

	public String updatePersonRole(long id, @Valid PersonRoleRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		PersonRoleEntity entity = personRoleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person Role not found with id: "+ id));
		if(dto.getPersonRoleName() != null) {
			entity.setPersonRoleName(dto.getPersonRoleName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			personRoleRepo.save(entity);
			return "Person Role Updated with "+dto.getPersonRoleName();
		} else {
			throw new ResourceNotFoundException("Provide Person Role to Update");
		}
	}

	public String deletePersonRole(long id) {
		PersonRoleEntity entity = personRoleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person Role not found with id: "+ id));
		personRoleRepo.delete(entity);
		return "Person Role Deleted from Database Successfully";
	}

}
