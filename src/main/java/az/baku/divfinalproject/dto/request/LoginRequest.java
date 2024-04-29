package az.baku.divfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@NotBlank
	private String phoneNumberOrEmail;

	@NotBlank
	private String password;
}
