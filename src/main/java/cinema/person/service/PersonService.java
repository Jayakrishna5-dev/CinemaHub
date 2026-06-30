package cinema.person.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.person.dto.PersonRequestDto;
import cinema.person.dto.PersonResponseDto;
import cinema.person.entity.PersonEntity;
import cinema.person.repository.PersonRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PersonService {
	private final PersonRepository personRepo;
	private final UserRepository userRepo;
	
	public PersonResponseDto savePerson(@Valid PersonRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRole() == null) {
			throw new ResourceNotFoundException("Role not found");
		}
		
		if(!personRepo.existsByName(dto.getName())) {
			PersonEntity entity = new PersonEntity();
			entity.setBio(dto.getBio());
			entity.setBirthDate(dto.getBirth_date());
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setName(dto.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			personRepo.save(entity);
			
			PersonResponseDto res = new PersonResponseDto();
			res.setAddedBy(user.getName());
			res.setName(entity.getName());
			res.setPersonId(entity.getId());
			res.setUserId(user.getId());
			return res;
			
		} else {
			throw new AlreadyExistsException("Person Role already exists");
		}		
	}

	public String updatePerson(long id, PersonRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		PersonEntity entity = personRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: "+ id));
		String message = "";
		if(dto.getBio() != null) {
			entity.setBio(dto.getBio());
			message += "Bio Updated ";
		}
		if(dto.getBirth_date() != null) {
			entity.setBirthDate(dto.getBirth_date());
			message += "Date of Birth Updated ";
		}
		if(dto.getName() != null) {
			entity.setName(dto.getName());
			message += "Name Updated ";
		}
		if(!message.equals("")) {
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			personRepo.save(entity);
			return message;
		} else {
			throw new ResourceNotFoundException("Provide Person details to update");
		}
	}

	public String deletePerson(long id) {
		PersonEntity entity = personRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: "+ id));
		personRepo.delete(entity);
		return "Person Data Deleted Successfully";
	}

}
