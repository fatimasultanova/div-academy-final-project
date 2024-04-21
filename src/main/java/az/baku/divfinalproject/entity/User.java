package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(length = 50)
    private String middleName;
    @Column(nullable = false,length = 100)
    private String lastName;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(length = 17)
    private String lastLoginIp;
    @Column(nullable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean isActive;
    @OneToOne
    private Subscription subscription;
    @OneToMany
    private List<Roles> roles;
    @OneToMany(cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    private List<Advert> adverts;
}
