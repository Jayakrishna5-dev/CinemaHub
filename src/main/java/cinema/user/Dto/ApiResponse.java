package cinema.user.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
	private String message;
    private T data;
}
