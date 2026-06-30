package cinema.movie.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cinema.MovieGenre.entity.MovieGenreEntity;
import cinema.common.auditVariables.AuditVariables;
import cinema.favorite.entity.FavoriteEntity;
import cinema.moviePerson.entity.MoviePersonEntity;
import cinema.rating.entity.RatingEntity;
import cinema.review.entity.ReviewEntity;
import cinema.user.userEntity.UserEntity;
import cinema.watchlistMovies.entity.WatchlistMoviesEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "movies")
public class MovieEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Column(nullable = false)
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "Release date is required")
    @PastOrPresent(message = "Release date cannot be in the future")
    @Column(nullable = false)
    private LocalDate releaseDate;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1000, message = "Duration cannot exceed 1000 minutes")
    @Column(nullable = false)
    private Integer durationMinutes;

    @NotBlank(message = "Language is required")
    @Size(max = 50)
    @Column(nullable = false)
    private String language;

    @NotBlank(message = "Country is required")
    @Size(max = 100)
    @Column(nullable = false)
    private String country;
    
    @Column(name = "imdb_rating")
    private BigDecimal imdbRating;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @OneToMany(
    	    mappedBy = "movie",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    )
    private List<MovieGenreEntity> movieGenres = new ArrayList<>();
    
    @OneToMany(
    	    mappedBy = "movie",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    )
    private List<MoviePersonEntity> moviePersons = new ArrayList<>();
    
    @OneToMany(
    	    mappedBy = "movie",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    )
    private List<WatchlistMoviesEntity> watchlistMovies = new ArrayList<>();
    
    @OneToMany(
    	    mappedBy = "movie",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    )
    private List<RatingEntity> ratings = new ArrayList<>();
    
    @OneToMany(
    	    mappedBy = "movie",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    )
    private List<FavoriteEntity> favorites = new ArrayList<>();
    
    @OneToMany(
    	    mappedBy = "movie",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    )
    private List<ReviewEntity> reviews = new ArrayList<>();
}
