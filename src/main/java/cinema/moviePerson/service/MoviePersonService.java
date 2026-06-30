package cinema.moviePerson.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.moviePerson.dto.MovieListResponseDto;
import cinema.moviePerson.dto.MoviePersonRequestDto;
import cinema.moviePerson.dto.NoAuthMoviePersonResponseDto;
import cinema.moviePerson.entity.MoviePersonEntity;
import cinema.moviePerson.repository.MoviePersonRepository;
import cinema.person.entity.PersonEntity;
import cinema.person.repository.PersonRepository;
import cinema.personRole.entity.PersonRoleEntity;
import cinema.personRole.repository.PersonRoleRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MoviePersonService {
	private final MoviePersonRepository moviePersonRepo;
	private final UserRepository userRepo;
	private final MovieRepository movieRepo;
	private final PersonRepository personRepo;
	private final PersonRoleRepository personRoleRepo;
	
	public String saveMoviePerson(@Valid MoviePersonRequestDto dto) {
		if(moviePersonRepo.existsByMovieIdAndPersonIdAndPersonRoleId(
		        dto.getMovieId(),
		        dto.getPersonId(),
		        dto.getPersonRoleId())) {
		    throw new AlreadyExistsException("Person already assigned to movie");
		} else {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
			if(user.getRole() == null) {
				throw new ResourceNotFoundException("Role not found");
			}
			
			MovieEntity movie = movieRepo.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			PersonEntity person = personRepo.findById(dto.getPersonId()).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
			PersonRoleEntity personRole = personRoleRepo.findById(dto.getPersonRoleId()).orElseThrow(() -> new ResourceNotFoundException("Person Role not found"));
			
			MoviePersonEntity entity = new MoviePersonEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			entity.setMovie(movie);
			entity.setPerson(person);
			entity.setPersonRole(personRole);
			moviePersonRepo.save(entity);
			
			return "Person & thier role attached to movie";
			
		}
	}

	public NoAuthMoviePersonResponseDto getPersonById(long id) {
		
		List<MoviePersonEntity> mpEntity = moviePersonRepo.findByPersonId(id);
		
		if(!mpEntity.isEmpty()) {
			NoAuthMoviePersonResponseDto res = new NoAuthMoviePersonResponseDto();
			PersonEntity person = personRepo.findById(mpEntity.get(0).getPerson().getId()).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
			res.setBio(person.getBio());
			res.setBirthDate(person.getBirthDate());
			res.setName(person.getName());
			res.setPersonId(person.getId());
			
			Map<Long, MovieListResponseDto> movieMap = new LinkedHashMap<>();

			for(MoviePersonEntity mp : mpEntity) {

			    MovieEntity movie = mp.getMovie();

			    MovieListResponseDto dto = movieMap.get(movie.getId());

			    if(dto == null) {
			        dto = new MovieListResponseDto();
			        dto.setMovieId(movie.getId());
			        dto.setTitle(movie.getTitle());
			        dto.setReleaseDate(movie.getReleaseDate());
			        dto.setRoles(new ArrayList<>());

			        movieMap.put(movie.getId(), dto);
			    }
			    dto.getRoles().add(mp.getPersonRole().getPersonRoleName());
			}
			
			res.setMovies(new ArrayList<>(movieMap.values()));
			
			return res;
		} else {
			throw new ResourceNotFoundException("Person not found");
		}
	}

	public NoAuthMoviePersonResponseDto getMovieDetailsFromPerson(String name) {
		PersonEntity personEntity = personRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
		if(personEntity != null) {
			return getPersonById(personEntity.getId());
		} else {
			throw new ResourceNotFoundException("Person not found in the Database");
		}
	}

	public List<String> getPersons() {
		List<PersonEntity> persons = personRepo.findAll();
		if(persons.isEmpty()) {
			throw new ResourceNotFoundException("No Person List exist in Database");
		} 
		
		return persons.stream()
                .map(PersonEntity::getName)
                .toList();
	}

	public String deleteMoviePerson(long id) {
		MoviePersonEntity entity = moviePersonRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person & Movie attachment not found"));
		moviePersonRepo.delete(entity);
		return "Movie & Person Attachment deleted successfully";
	}

}
