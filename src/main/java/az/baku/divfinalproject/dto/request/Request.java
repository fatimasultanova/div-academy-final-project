package az.baku.divfinalproject.dto.request;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Request<R> {
    private long id;
    private R request;
}
