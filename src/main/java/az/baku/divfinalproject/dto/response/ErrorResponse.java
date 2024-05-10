package az.baku.divfinalproject.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String type;
    private LocalDateTime localTime;
    private String errorMessage;

    public ErrorResponse(HttpStatus httpStatus, LocalDateTime localTime, String errorMessage) {
        this.httpStatus = httpStatus;
        this.localTime = localTime;
        this.errorMessage = errorMessage;
        this.type="ERROR";
}
}
