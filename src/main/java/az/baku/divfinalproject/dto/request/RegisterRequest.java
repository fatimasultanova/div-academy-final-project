package az.baku.divfinalproject.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequest {
    @NotBlank(message = "Firstname not be blank")
    @Size(min = 2, max = 50,message = "Firstname must be longer 2 and shorter 50")
    private String firstName;
    @Size(min = 2, max = 50,message = "Middle name must be longer 2 and shorter 50")
    private String middleName;
    @Size(min = 2, max = 50, message = "Lastname must be longer 2 and shorter 50")
    //@NotBlank(message = "Lastname not be blank")
    private String lastName;
    private LocalDate birthDate;
    @Size(max = 50, message = "Email must be shorter 50")
    @Email(message = "Invalid Email")
    private String email;
    private String phoneNumber;
    @Size(min = 6, max = 40 , message = "Password must be longer 6 and shorter 40")
    @NotBlank(message = "Password not be blank")
    private String password;
}
