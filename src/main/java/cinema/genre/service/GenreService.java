package cinema.genre.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.MovieGenre.entity.MovieGenreEntity;
import cinema.MovieGenre.repository.MovieGenreRepository;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.genre.dto.GenreRequestDto;
import cinema.genre.dto.GenreResponseDto;
import cinema.genre.entity.GenreEntity;
import cinema.genre.repository.GenreRepository;
import cinema.movie.dto.NoAuthMovieResponseDto;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.movie.service.MovieService;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {
	private final GenreRepository genreRepo;
	private final UserRepository userRepo;
	private final MovieGenreRepository mgRepo;
	private final MovieRepository movieRepo;
	private final MovieService movieService;

	public GenreResponseDto saveGenre(GenreRequestDto dto) {
		GenreEntity genre = new GenreEntity();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		genre.setCreatedAt(LocalDateTime.now());
		genre.setCreatedBy(user.getName());
		genre.setDescription(dto.getDescription());
		genre.setGenreName(dto.getGenreName());
		genre.setUpdatedAt(LocalDateTime.now());
		genre.setUpdatedBy(user.getName());
		genreRepo.save(genre);
		GenreResponseDto res = new GenreResponseDto();
		res.setCreatedAt(LocalDateTime.now());
		res.setGenreName(dto.getGenreName());
		res.setMessage("Genre Added into Database Successfully");
		return res;
	}

	public List<String> getGenres() {
		List<GenreEntity> genres = genreRepo.findAll();
		if(genres.isEmpty()) {
			throw new ResourceNotFoundException("Genre list does not exist in database");
		} 
		
		return genres.stream()
                .map(GenreEntity::getGenreName)
                .toList();
	}

	public List<String> getMoviesByGenre(String genre) {
		GenreEntity genreEntity = genreRepo.findByGenreName(genre).orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
		List<MovieGenreEntity> mgentity = mgRepo.findByGenreId(genreEntity.getId());
		if(mgentity.isEmpty()) {
			throw new ResourceNotFoundException("Genre is Empty");
		} 
		List<String> movieslist = new ArrayList<>();
		for(MovieGenreEntity mg : mgentity) {
			MovieEntity movie = mg.getMovie();
			if(movieslist.contains(movie.getTitle())) {
				continue;
			}
			movieslist.add(movie.getTitle());
		}
		
		return movieslist;
	}

	public NoAuthMovieResponseDto getMovieDetailsByGenre(String movie) {
		MovieEntity movieEntity = movieRepo.findByTitle(movie).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		if(movie != null) {
			return movieService.getMovieById(movieEntity.getId());
		} else {
			throw new ResourceNotFoundException("Movie not found");
		}
	}


	public String updateGenre(long id, GenreRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		GenreEntity genreEntity = genreRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
		String message = "";
		if(dto.getDescription() != null) {
			genreEntity.setDescription(dto.getDescription());
			message += "Description Updated for "+genreEntity.getGenreName();
		}
		if(dto.getGenreName() != null) {
			genreEntity.setGenreName(dto.getGenreName());
			message += " Genre Updated from "+genreEntity.getGenreName()+" to "+dto.getGenreName();
		}
		if(!message.equals("")) {
			genreEntity.setUpdatedAt(LocalDateTime.now());
			genreEntity.setUpdatedBy(user.getName());
			genreRepo.save(genreEntity);
			return message;
		} else {
			throw new ResourceNotFoundException("Provide genre name to update");
		}
	}

	public String deleteGenre(long id) {
		GenreEntity genreEntity = genreRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
		genreRepo.delete(genreEntity);
		return "Genre Deleted from database successfully";
	}

}
