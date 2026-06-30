package cinema.Watchlist.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cinema.Watchlist.dto.RenameWatchlistDto;
import cinema.Watchlist.dto.WatchlistRequestDto;
import cinema.Watchlist.dto.WatchlistResponseDto;
import cinema.Watchlist.entity.WatchlistEntity;
import cinema.Watchlist.repository.WatchlistRepository;
import cinema.exception.customException.AccessDeniedException;
import cinema.exception.customException.AlreadyExistsException;
import cinema.exception.customException.ResourceNotFoundException;
import cinema.movie.dto.NoAuthMovieResponseDto;
import cinema.movie.entity.MovieEntity;
import cinema.movie.repository.MovieRepository;
import cinema.movie.service.MovieService;
import cinema.user.repository.UserRepository;
import cinema.user.userEntity.UserEntity;
import cinema.watchlistMovies.entity.WatchlistMoviesEntity;
import cinema.watchlistMovies.repository.WatchlistMoviesRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class WatchlistService {
	private final WatchlistRepository watchlistRepo;
	private final UserRepository userRepo;
	private final WatchlistMoviesRepository wmRepo;
	private final MovieRepository movieRepo;
	private final MovieService movieService;

	public WatchlistResponseDto saveWatchlist(@Valid WatchlistRequestDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		if(user.getRole() == null) {
			throw new ResourceNotFoundException("Role not found");
		}
		if(!watchlistRepo.existsByNameAndUserId(dto.getName(), user.getId())) {
			WatchlistEntity entity = new WatchlistEntity();
			entity.setCreatedAt(LocalDateTime.now());
			entity.setCreatedBy(user.getName());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(user.getName());
			entity.setUser(user);
			entity.setName(dto.getName());
			watchlistRepo.save(entity);
			
			WatchlistResponseDto res = new WatchlistResponseDto();
			res.setCreatedBy(user.getName());
			res.setUserId(user.getId());
			res.setWatchlist(entity.getName());
			res.setWatchlistId(entity.getId());
			return res;
			
		} else {
			throw new AlreadyExistsException("Watchlist already exists");
		}
	}

	public List<String> getAllWatchlist() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<WatchlistEntity> watchlistEntity = watchlistRepo.findAllByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		List<String> watchlist = new ArrayList<>();
		for(WatchlistEntity e : watchlistEntity) {
			watchlist.add(e.getName());
		}
		return watchlist;
	}

	public List<String> getWatchlistMovies(String watchlist) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		WatchlistEntity watchlistEntity = watchlistRepo.findByName(watchlist).orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
		List<WatchlistMoviesEntity> wmEntity = wmRepo.findAllByUserIdAndWatchlistId(user.getId(), watchlistEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
		List<String> movies = new ArrayList<>();
		for(WatchlistMoviesEntity e : wmEntity) {
			movies.add(e.getMovie().getTitle());
		}
		return movies;
	}

	public NoAuthMovieResponseDto getWatchlistMovieDetails(String movie) {
		MovieEntity movieEntity = movieRepo.findByTitle(movie).orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		if(movie != null) {
			return movieService.getMovieById(movieEntity.getId());
		} else {
			throw new ResourceNotFoundException("Movie not found");
		}
	}

	@Transactional
	public String removeMovieFromWatchlist(long watchlistId, long movieId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		WatchlistEntity entity = watchlistRepo.findById(watchlistId).orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
		if (!entity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this watchlist.");
		}
		int deleted = wmRepo.deleteByWatchlistIdAndMovieId(watchlistId, movieId);
	    if (deleted == 0) {
	        throw new ResourceNotFoundException("Movie not found in watchlist");
	    }

	    return "Movie removed from watchlist";
	}

	public String renameWatchlist(String watchlist, RenameWatchlistDto dto) {
		if(watchlist.equals(dto.getNewWatchlistName())) {
			throw new AlreadyExistsException("The new watchlist name must be different from the current name.");
		}
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		WatchlistEntity watchlistEntity = watchlistRepo.findByNameAndUserId(watchlist, user.getId()).orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
		if(dto.getNewWatchlistName() != null) {
			watchlistEntity.setName(dto.getNewWatchlistName());
			watchlistEntity.setUpdatedAt(LocalDateTime.now());
			watchlistEntity.setUpdatedBy(user.getName());
			watchlistEntity.setUser(user);
			watchlistRepo.save(watchlistEntity);
			return watchlist + " name updated to -> " + dto.getNewWatchlistName();
		}
		throw new ResourceNotFoundException("new watchlist name is missing");
	}

	@Transactional
	public String deleteWatchlist(long id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserEntity user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		WatchlistEntity entity = watchlistRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Watchlist not found"));
		if (!entity.getUser().getId().equals(user.getId())) {
		    throw new AccessDeniedException("You cannot modify this watchlist.");
		}
		watchlistRepo.delete(entity);

	    return "Watchlist Deleted Successfully";
	}
}
