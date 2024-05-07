package az.baku.divfinalproject.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserResponse {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private boolean active;
    private boolean blockedByAdmin;
    private boolean deleted;
    private SubscriptionResponse subscription;
    private Set<String> roles;
    private Set<Long> adverts;
    private Set<Long> viewedAdverts;
}
