package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SubscriptionRequest {
    private String type;
    private String description;
    private long amount;
    private int requestCount;
}