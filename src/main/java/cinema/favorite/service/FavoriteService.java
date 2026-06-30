package cinema.favorite.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.exception.customException.AccessDeniedException;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.favorite.dto.FavoriteRequestDto;
import cinema.favorite.dto.FavoriteResponseDto;
import cinema.favorite.entity.FavoriteEntity;
import cinema.favorite.repository.FavoriteRepository;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class FavoriteService {
	private final FavoriteRepository favRepo;
	private final UserRepository userRepo;
	private final MovieRepository movieRepo;

	public FavoriteResponseDto saveFavorite(@Valid FavoriteRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRole() == null) {
			throw new ResourceNotFoundException("Role not found");
		}
		if(!favRepo.existsByMovieIdAndUserId(dto.getMovieId(), user.getId())) {
			FavoriteEntity entity = new FavoriteEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			MovieEntity movie = movieRepo.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			entity.setMovie(movie);
			favRepo.save(entity);
			
			return toDto(entity, user, movie);
			
		} else {
			throw new AlreadyExistsException("This movie already exists");
		}
	}

	private FavoriteResponseDto toDto(FavoriteEntity entity, UserEntity user, MovieEntity movie) {
		FavoriteResponseDto res = new FavoriteResponseDto();
		res.setAddedBy(user.getName());
		res.setMovie(movie.getTitle());
		res.setMovieId(movie.getId());
		res.setUserId(user.getId());
		return res;
	}

	public List<FavoriteResponseDto> getFavoriteMovies() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<FavoriteEntity> favEntity = favRepo.findAllByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<FavoriteResponseDto> response = new ArrayList<>();
		for(FavoriteEntity f : favEntity) {
			MovieEntity movie = movieRepo.findById(f.getMovie().getId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			response.add(toDto(f, user, movie));
		}
		return response;
	}

	@Transactional
	public String removeMovieFromFavorite(long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		FavoriteEntity entity = favRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Favorite Movie not found"));
		if (!entity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this watchlist.");
		}
		favRepo.delete(entity);

	    return "Movie removed from watchlist";
	}
}
