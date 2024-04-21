package az.baku.divfinalproject.exception;

import az.baku.divfinalproject.dto.response.ExceptionResponse;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ExceptionResponse exceptionResponse;

    public ApplicationException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getMessage());
        this.exceptionResponse = exceptionResponse;
    }

}
