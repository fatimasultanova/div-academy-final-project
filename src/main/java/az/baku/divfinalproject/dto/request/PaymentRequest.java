package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentRequest {
    private long userId;
    private long amount;
    private String currency;
    private String payment_method;
    private String secretKey;
}
