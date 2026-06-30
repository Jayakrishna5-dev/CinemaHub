package cinema.Watchlist.entity;

import java.util.ArrayList;
import java.util.List;

import cinema.common.auditVariables.AuditVariables;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "watchlist")
@Getter
@Setter
public class WatchlistEntity extends AuditVariables {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 100)
    private String name;
    
    @OneToMany(
    	mappedBy = "watchlist",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<WatchlistMoviesEntity> watchlistMovies = new ArrayList<>();
}
