package az.baku.divfinalproject.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class SignupRequest {
  @NotBlank
  @Size(min = 2, max = 50)
  private String firstName;
  @NotBlank
  @Size(min = 2, max = 50)
  private String middleName;
  @NotBlank
  @Size(min = 2, max = 50)
  private String lastName;
  private LocalDate birthDate;
  @NotBlank
  private String phoneNumber;
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;
  @Size(max = 50)
  @Email
  private String email;

  //private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
}
