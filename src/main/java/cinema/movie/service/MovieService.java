package cinema.movie.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.MovieGenre.entity.MovieGenreEntity;
import cinema.MovieGenre.repository.MovieGenreRepository;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.genre.repository.GenreRepository;
import cinema.movie.dto.ApiResponse;
import cinema.movie.dto.CastResponseDto;
import cinema.movie.dto.MovieRequestDto;
import cinema.movie.dto.MovieResponseDto;
import cinema.movie.dto.NoAuthMovieResponseDto;
import cinema.movie.entity.MovieEntity;
import cinema.movie.mapper.MovieMapper;
import cinema.movie.repository.MovieRepository;
import cinema.moviePerson.entity.MoviePersonEntity;
import cinema.moviePerson.repository.MoviePersonRepository;
import cinema.person.entity.PersonEntity;
import cinema.person.repository.PersonRepository;
import cinema.personRole.entity.PersonRoleEntity;
import cinema.personRole.repository.PersonRoleRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MovieService {
	private final MovieRepository movieRepo;
	private final UserRepository userRepo;
	private final MovieGenreRepository mgRepo;
	private final GenreRepository genreRepo;
	private final MoviePersonRepository moviePersonRepo;
	private final PersonRepository personRepo;
	private final PersonRoleRepository personRoleRepo;
	
	public MovieResponseDto saveMovie(@Valid MovieRequestDto dto) {
		if(!movieRepo.existsByTitle(dto.getTitle())) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

			MovieMapper mapper = new MovieMapper();
			
			MovieEntity entity = mapper.toEntity(dto, user);
			movieRepo.save(entity);
			return mapper.toResponse(entity);
		} else {
			throw new AlreadyExistsException("Movie already exists");
		}
	}

	public NoAuthMovieResponseDto getMovieById(long id) {
		MovieEntity movie = movieRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return processMovie(movie);
	}
	
	//Get all movies with Pagination
	public Page<NoAuthMovieResponseDto> getMovies(int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    Page<MovieEntity> movies = movieRepo.findAll(pageable);

	    return movies.map(this::processMovie);
	}

	//Get all movies without Pagination
	public List<NoAuthMovieResponseDto> getMovies() {
		List<NoAuthMovieResponseDto> response = new ArrayList<>();
		List<MovieEntity> movie = movieRepo.findAll();
		for(MovieEntity m : movie) {
			response.add(processMovie(m));
		}
		return response;
	}

	public NoAuthMovieResponseDto processMovie(MovieEntity movie) {
		if(movie != null) {
			List<MovieGenreEntity> movieGenre = mgRepo.findAllByMovieId(movie.getId());
			List<String> genres = movieGenre.stream()
			        .map(mg -> genreRepo.findById(mg.getGenre().getId())
			                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"))
			                .getGenreName())
			        .toList();		
			
			List<MoviePersonEntity> moviePersons = moviePersonRepo.findAllByMovieId(movie.getId());
			List<CastResponseDto> cast = new ArrayList<>();

			for (MoviePersonEntity mp : moviePersons) {

			    PersonEntity person = personRepo.findById(mp.getPerson().getId())
			            .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

			    PersonRoleEntity role = personRoleRepo.findById(mp.getPersonRole().getId())
			            .orElseThrow(() -> new ResourceNotFoundException("Person Role not found"));

			    CastResponseDto dto = new CastResponseDto();
			    dto.setPersonId(person.getId());
			    dto.setName(person.getName());
			    dto.setRole(role.getPersonRoleName());
			    dto.setBio(person.getBio());

			    cast.add(dto);
			}
			
			NoAuthMovieResponseDto res = new NoAuthMovieResponseDto();
			res.setCountry(movie.getCountry());
			res.setDescription(movie.getDescription());
			res.setDurationMinutes(movie.getDurationMinutes());
			res.setImdbRating(movie.getImdbRating());
			res.setLanguage(movie.getLanguage());
			res.setMovieGenres(genres);
			res.setMovieId(movie.getId());
			res.setMovieName(movie.getTitle());
			res.setCastAndCrew(cast);
			res.setReleaseDate(movie.getReleaseDate());
			
			return res;
		} else {
			throw new ResourceNotFoundException("Movie not found");
		}
	}

	public ApiResponse<MovieResponseDto> updateMovie(long id, MovieRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		MovieEntity movie = movieRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		String message = "";
		if(dto.getCountry() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getCountry(), dto.getCountry());
			movie.setCountry(dto.getCountry());
		}
		if(dto.getDescription() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getDescription(), dto.getDescription());
			movie.setDescription(dto.getDescription());
		}
		if(dto.getDurationMinutes() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getDurationMinutes(), dto.getDurationMinutes());
			movie.setDurationMinutes(dto.getDurationMinutes());
		}
		if(dto.getImdbRating() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getImdbRating(), dto.getImdbRating());
			movie.setImdbRating(dto.getImdbRating());
		}
		if(dto.getLanguage() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getLanguage(), dto.getLanguage());
			movie.setLanguage(dto.getLanguage());
		}
		if(dto.getReleaseDate() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getReleaseDate(), dto.getReleaseDate());
			movie.setReleaseDate(dto.getReleaseDate());
		}
		if(dto.getTitle() != null) {
			message += "[%s is updated to %s] "
			        .formatted(movie.getTitle(), dto.getTitle());
			movie.setTitle(dto.getTitle());
		}
		if(!message.equals("")) {
			movie.setUpdatedAt(LocalDateTime.now());
			movie.setUpdatedBy(user.getName());
			movie.setUser(user);
			movieRepo.save(movie);
			ApiResponse<MovieResponseDto> res = new ApiResponse<>();
			MovieMapper mapper = new MovieMapper();
			res.setMessage(message);
			res.setData(mapper.toResponse(movie));
			return res;
		} else {
			throw new ResourceNotFoundException("No Data to update");
		}
		
	}

	@Transactional
	public String deleteMovie(long id) {
		MovieEntity movie = movieRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
		movieRepo.delete(movie);

	    return "Movie Deleted from Database";
	}
}
