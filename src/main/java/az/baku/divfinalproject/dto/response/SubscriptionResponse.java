package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubscriptionResponse {
    private long id;
    private String type;
    private String description;
    private double amount;
    private int requestCount;
}
