package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdvertTypeResponse {
    private long id;
    private String description;
    private String type;
}
