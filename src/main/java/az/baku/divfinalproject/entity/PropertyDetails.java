package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PropertyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private PropertyType propertyType;
    private int numberFloors;
    private int roomFloor;
    private boolean gas;
    private boolean elevator;
    @OneToOne
    private BuildingType buildingType;
    @Column(length = 500)
    private String address;


}
