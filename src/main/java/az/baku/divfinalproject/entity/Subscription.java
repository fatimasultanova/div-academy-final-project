package az.baku.divfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(schema = "public", name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private String description;
    private long amount;
    private int requestCount;

    public Subscription(String type, String description, long amount, int requestCount) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.requestCount = requestCount;
    }
}
