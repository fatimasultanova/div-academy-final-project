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
    @Column(nullable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;
    private double amountMonthly;
    @OneToOne(cascade = {CascadeType.ALL})
    private AdvertDetails advertDetails;
    @ManyToOne
    private User user;



}
