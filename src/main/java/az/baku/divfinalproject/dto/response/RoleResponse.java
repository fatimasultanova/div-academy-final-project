package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleResponse {
    private long id;
    private String name;
}
