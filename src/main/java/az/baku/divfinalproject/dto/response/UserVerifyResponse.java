package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserVerifyResponse {
    private long id;
    private String token;
    private boolean isActive;
    private long createTime;
    private long endTime;
}
