package cinema.movie.mapper;

import java.time.LocalDateTime;

import cinema.movie.dto.MovieRequestDto;
import cinema.movie.dto.MovieResponseDto;
import cinema.movie.entity.MovieEntity;
import cinema.user.userEntity.UserEntity;
import jakarta.validation.Valid;

public class MovieMapper {

	public MovieEntity toEntity(@Valid MovieRequestDto dto, UserEntity user) {
		MovieEntity entity = new MovieEntity();
		entity.setCountry(dto.getCountry());
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(user.getName());
		entity.setDescription(dto.getDescription());
		entity.setDurationMinutes(dto.getDurationMinutes());
		entity.setLanguage(dto.getLanguage());
		entity.setReleaseDate(dto.getReleaseDate());
		entity.setTitle(dto.getTitle());
		entity.setUpdatedAt(LocalDateTime.now());
		entity.setUpdatedBy(user.getName());
		entity.setUser(user);
		entity.setImdbRating(dto.getImdbRating());
		return entity;
	}

	public MovieResponseDto toResponse(MovieEntity entity) {
		MovieResponseDto response = new MovieResponseDto();
		response.setCountry(entity.getCountry());
		response.setDescription(entity.getDescription());
		response.setDurationMinutes(entity.getDurationMinutes());
		response.setId(entity.getId());
		response.setLanguage(entity.getLanguage());
		response.setReleaseDate(entity.getReleaseDate());
		response.setTitle(entity.getTitle());
		response.setUserId(entity.getUser().getId());
		response.setUserName(entity.getUser().getName());
		response.setImdbRating(entity.getImdbRating());
		return response;
	}

}
