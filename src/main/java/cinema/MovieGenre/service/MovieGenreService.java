package cinema.MovieGenre.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import cinema.MovieGenre.dto.MovieGenreRequestDto;
import cinema.MovieGenre.dto.MovieGenreResponseDto;
import cinema.MovieGenre.entity.MovieGenreEntity;
import cinema.MovieGenre.repository.MovieGenreRepository;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.genre.entity.GenreEntity;
import cinema.genre.repository.GenreRepository;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class MovieGenreService {
	private final MovieGenreRepository movieGenreRepo;
	private final UserRepository userRepo;
	private final MovieRepository movieRepo;
	private final GenreRepository genreRepo;
	
	public MovieGenreResponseDto saveMovieGenre(@Valid MovieGenreRequestDto dto) {
		if(movieGenreRepo.existsByMovieIdAndGenreId(
		        dto.getMovieId(),
		        dto.getGenreId())) {
		    throw new AlreadyExistsException("Genre already assigned to movie");
		} else {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
			if(user.getRole() == null) {
				throw new ResourceNotFoundException("Role not found");
			}
			
			MovieEntity movie = movieRepo.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			GenreEntity genre = genreRepo.findById(dto.getGenreId()).orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
			
			MovieGenreEntity entity = new MovieGenreEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			entity.setMovie(movie);
			entity.setGenre(genre);
			movieGenreRepo.save(entity);
			
			MovieGenreResponseDto response = new MovieGenreResponseDto();
			response.setMovieTitle(entity.getMovie().getTitle());
			List<String> genres = movie.getMovieGenres()
		            .stream()
		            .map(movieGenre -> movieGenre.getGenre().getGenreName())
		            .toList();
			response.setGenres(genres);
			response.setAddedBy(entity.getUser().getName());
			
			return response;
			
		}
	}

	public String deleteMovieGenre(long id) {
		MovieGenreEntity entity = movieGenreRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie & Genre Attachment is not found"));
		movieGenreRepo.delete(entity);
		return "Movie & Genre attachment deleted successfully";
	}
	
}
