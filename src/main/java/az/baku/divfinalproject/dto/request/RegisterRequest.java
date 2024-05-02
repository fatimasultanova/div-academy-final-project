package az.baku.divfinalproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;
    @Size(min = 2, max = 50)
    private String middleName;
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;
    private LocalDate birthDate;
    @Size(max = 50)
    @Email
    private String email;
    private String phoneNumber;
    @Size(min = 6, max = 40)
    @NotBlank
    private String password;
}
