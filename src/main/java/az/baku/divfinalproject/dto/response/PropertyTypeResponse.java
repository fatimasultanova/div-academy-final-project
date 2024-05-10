package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PropertyTypeResponse {
    private long id;
    private String description;
    private String type;

}
