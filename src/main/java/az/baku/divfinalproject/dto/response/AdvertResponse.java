package az.baku.divfinalproject.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdvertResponse{
    private long id;
    private String description;
    private long advertTypeId;
    private long propertyDetailsId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;
    private double amountMonthly;
    private long advertDetailsId;
    private long user;
}
