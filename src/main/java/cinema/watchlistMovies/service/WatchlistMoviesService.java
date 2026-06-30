package cinema.watchlistMovies.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.Watchlist.entity.WatchlistEntity;
import cinema.Watchlist.repository.WatchlistRepository;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import cinema.watchlistMovies.dto.WatchlistMoviesRequestDto;
import cinema.watchlistMovies.dto.WatchlistMoviesResponseDto;
import cinema.watchlistMovies.entity.WatchlistMoviesEntity;
import cinema.watchlistMovies.repository.WatchlistMoviesRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class WatchlistMoviesService {
	private final WatchlistMoviesRepository wmRepo;
	private final UserRepository userRepo;
	private final MovieRepository movieRepo;
	private final WatchlistRepository wlRepo;

	public WatchlistMoviesResponseDto saveWatchlistMovies(@Valid WatchlistMoviesRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRole() == null) {
			throw new ResourceNotFoundException("Role not found");
		}
		if(!wmRepo.existsByWatchlistIdAndMovieIdAndUserId(dto.getWatchlistId(), dto.getMovieId(), user.getId())) {
			WatchlistMoviesEntity entity = new WatchlistMoviesEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			MovieEntity movie = movieRepo.findById(dto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
			WatchlistEntity watchlist = wlRepo.findById(dto.getWatchlistId()).orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
			entity.setMovie(movie);
			entity.setWatchlist(watchlist);
			wmRepo.save(entity);
			
			WatchlistMoviesResponseDto res = new WatchlistMoviesResponseDto();
			res.setAddedBy(user.getName());
			res.setMovie(movie.getTitle());
			res.setUserId(user.getId());
			res.setWatchlist(watchlist.getName());
			res.setWatchlistId(watchlist.getId());
			return res;
			
		} else {
			throw new AlreadyExistsException("Movie already present in watchlist");
		}
	}
	
}
