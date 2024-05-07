package az.baku.divfinalproject.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String phoneNumber;
    private String email;
    private Set<String> roles;

    public JwtResponse(String token, Long id, String phoneNumber, String email, Set<String> roles) {
        this.token = token;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
    }
}
