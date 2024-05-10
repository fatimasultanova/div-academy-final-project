package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
