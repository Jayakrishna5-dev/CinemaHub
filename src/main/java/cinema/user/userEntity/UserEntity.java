package cinema.user.userEntity;


import java.util.ArrayList;
import java.util.List;

import cinema.Watchlist.entity.WatchlistEntity;
import cinema.common.auditVariables.AuditVariables;
import cinema.favorite.entity.FavoriteEntity;
import cinema.rating.entity.RatingEntity;
import cinema.review.entity.ReviewEntity;
import cinema.role.roleEntity.RoleEntity;
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
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends AuditVariables{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private RoleEntity role;
	
	@OneToMany(
	    mappedBy = "user",
	    cascade = CascadeType.ALL,
	    orphanRemoval = true
	)
	private List<WatchlistMoviesEntity> watchlistMovies = new ArrayList<>();
	
	@OneToMany(
		mappedBy = "user",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<WatchlistEntity> watchlist = new ArrayList<>();
	
	@OneToMany(
		mappedBy = "user",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<ReviewEntity> reviews = new ArrayList<>();
	
	@OneToMany(
		mappedBy = "user",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<RatingEntity> ratings = new ArrayList<>();
	
	@OneToMany(
    	mappedBy = "user",
    	cascade = CascadeType.ALL,
    	orphanRemoval = true
    )
    private List<FavoriteEntity> favorites = new ArrayList<>();
}
