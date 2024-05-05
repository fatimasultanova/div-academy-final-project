package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
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
    @OneToOne(fetch = FetchType.EAGER)
    private AdvertType advertType;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private PropertyDetails propertyDetails;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;
    private double amountMonthly;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private AdvertDetails advertDetails;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Advert() {
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.isActive = true;
    }
}
