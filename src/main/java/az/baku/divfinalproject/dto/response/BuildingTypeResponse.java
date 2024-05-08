package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class BuildingTypeResponse {
    private long id;
    private String description;
    private String type;

}
