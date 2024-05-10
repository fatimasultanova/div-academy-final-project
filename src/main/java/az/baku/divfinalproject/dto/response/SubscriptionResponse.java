package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SubscriptionResponse {
    private long id;
    private String type;
    private String description;
    private long amount;
    private int requestCount;
}
