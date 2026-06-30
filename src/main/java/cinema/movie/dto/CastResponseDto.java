package cinema.movie.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastResponseDto {
	private long personId;
    private String name;
    private String bio;
    private String role;
}
