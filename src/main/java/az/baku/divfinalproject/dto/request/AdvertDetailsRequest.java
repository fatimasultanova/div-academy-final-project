package az.baku.divfinalproject.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AdvertDetailsRequest {
    private LocalDate moveTime;
    private int livingCount;
    private int searchingCount;
    private String gender;
}
