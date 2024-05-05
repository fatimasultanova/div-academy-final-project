package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "public", name = "user")
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
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String phoneNumber;
    @Column(length = 17)
    private String lastLoginIp;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean deleted;
    private boolean blockedByAdmin;
    private boolean isActive;
    @OneToOne
    private Subscription subscription;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    private List<Advert> adverts = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    private List<Advert> viewedAdverts = new ArrayList<>();

    public User(String firstName,String middleName, String lastName,LocalDate birthDate, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.isActive = false;
        this.deleted = false;
        this.blockedByAdmin = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", deleted=" + deleted +
                ", isActive=" + isActive +
                ", subscription=" + subscription.toString() +
                ", roles=" + roles.toString() +
                ", adverts=" + adverts.toString() +
                '}';

    }
}
