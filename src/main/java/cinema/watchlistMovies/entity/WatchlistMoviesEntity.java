package cinema.watchlistMovies.entity;

import cinema.Watchlist.entity.WatchlistEntity;
import cinema.common.auditVariables.AuditVariables;
import cinema.movie.entity.MovieEntity;
import cinema.user.userEntity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "watchlist_movies")
@Getter
@Setter
public class WatchlistMoviesEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "watchlist_id", nullable = false)
    private WatchlistEntity watchlist;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable =false)
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
