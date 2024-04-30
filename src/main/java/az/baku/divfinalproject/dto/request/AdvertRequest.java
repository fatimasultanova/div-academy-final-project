package az.baku.divfinalproject.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertRequest {
    private String description;
    private long advertTypeId;
    private long propertyDetailsId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;
    private double amountMonthly;
    private long advertDetailsId;
    private long userId;
}
