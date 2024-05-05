package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(schema = "public", name = "advert_type")
public class AdvertType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 500)
    private String description;
    private String type;

    public static AdvertType fromString(String type) {
        if (type != null) {
            AdvertType advertType = new AdvertType();
            advertType.setType(type);
            return advertType;
        }
        return null;
    }

    public static String fromAdvertType(AdvertType type) {
            return type != null ? type.getType() : null;
    }
}
