package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(schema = "public", name = "advert")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 500)
    private String description;
    @OneToOne()
    private AdvertType advertType;
    @OneToOne(cascade = {CascadeType.ALL})
    private PropertyDetails propertyDetails;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;
    private double amountMonthly;
    @OneToOne(cascade = {CascadeType.ALL})
    private AdvertDetails advertDetails;
    @ManyToOne
    private User user;

    public Advert(LocalDateTime createDate,LocalDateTime updateDate) {
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.isActive = true;
    }
}
