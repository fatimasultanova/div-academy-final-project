package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdvertTypeRequest {
    private String description;
    private String type;
}
