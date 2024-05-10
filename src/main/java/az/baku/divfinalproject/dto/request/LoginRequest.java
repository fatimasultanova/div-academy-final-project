package az.baku.divfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequest {
	@NotBlank(message = "Not Blank")
	private String phoneNumberOrEmail;

	@NotBlank(message = "Not Blank")
	private String password;
}
