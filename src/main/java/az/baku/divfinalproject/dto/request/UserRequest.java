package az.baku.divfinalproject.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRequest {
  @Size(min = 2, max = 50)
  private String firstName;
  @Size(min = 2, max = 50)
  private String middleName;
  @Size(min = 2, max = 50)
  private String lastName;
  private LocalDate birthDate;
}
