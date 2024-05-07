package az.baku.divfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequest {
	@NotBlank
	private String phoneNumberOrEmail;

	@NotBlank
	private String password;
}
