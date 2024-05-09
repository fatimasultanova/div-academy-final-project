package az.baku.divfinalproject.dto.response;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class ExceptionResponse {
    String message;
    HttpStatus httpStatus;

    public ExceptionResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
