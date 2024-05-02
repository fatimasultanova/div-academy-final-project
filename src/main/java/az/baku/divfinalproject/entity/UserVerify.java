package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(schema = "public", name = "user_verify")
public class UserVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    @OneToOne
    private User user;
    private boolean isActive;
    private long createTime;
    private long endTime;

    public UserVerify() {
        this.token = UUID.randomUUID().toString();
        this.isActive = true;
        this.createTime = System.currentTimeMillis();
        this.endTime = 2 * 60 * 1000;
    }

    public boolean isTokenValid() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - createTime) <= endTime;
    }
}
