package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PropertyDetailsRequest {
    int numberFloors;
    int roomFloor;
    boolean gas;
    long square_m;
    boolean elevator;
    String address;
}