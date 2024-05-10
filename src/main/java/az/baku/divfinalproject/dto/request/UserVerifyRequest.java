package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserVerifyRequest {
    private String token;
    private boolean isActive;
    private long createTime;
    private long endTime;
    private long userId;
}
