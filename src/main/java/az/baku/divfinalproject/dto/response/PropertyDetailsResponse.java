package az.baku.divfinalproject.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PropertyDetailsResponse{
    long id;
    int numberFloors;
    int roomFloor;
    boolean gas;
    long square_m;
    boolean elevator;
    String address;
}