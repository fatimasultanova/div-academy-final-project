package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class BuildingTypeRequest {
    private String description;
    private String type;
}
