package az.baku.divfinalproject.dto.request;

import az.baku.divfinalproject.dto.response.AdvertResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdvertRequest {
    private String description;
    private String advertType;
    private long propertyDetailsId;
    private LocalDateTime updateDate;
    private boolean isActive;
    private double amountMonthly;
    private long advertDetailsId;
    private long userId;
}
