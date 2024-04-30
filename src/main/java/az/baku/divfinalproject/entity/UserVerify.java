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
@Table(schema = "public", name = "user_verify")
public class UserVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime endTime;
    private boolean success;
    private boolean isActive;
    private String token;
    @OneToOne
    private User user;
}
