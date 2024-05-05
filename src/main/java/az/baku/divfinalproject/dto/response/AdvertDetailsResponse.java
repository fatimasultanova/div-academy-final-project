package az.baku.divfinalproject.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AdvertDetailsResponse {
    private long id;
    private LocalDate moveTime;
    private int livingCount;
    private int searchingCount;
    private String gender;
}
