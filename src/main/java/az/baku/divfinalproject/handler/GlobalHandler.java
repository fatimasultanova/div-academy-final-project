package az.baku.divfinalproject.handler;

import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.exception.ApplicationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandler extends DefaultErrorAttributes {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException ex) {
        ExceptionResponse exceptionResponse = ex.getExceptionResponse();
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    private Map<String, Object> buildErrorAttributes(ExceptionResponse exceptionResponse, WebRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        errorAttributes.put("error", exceptionResponse.getMessage());
        errorAttributes.put("status", exceptionResponse.getHttpStatus().value());
        errorAttributes.put("path", request.getDescription(false));
        return errorAttributes;
    }


    private ResponseEntity<Map<String, Object>> of (ApplicationException exception, WebRequest webRequest) {

        Map<String, Object> errorAttributes  = buildErrorAttributes(exception.getExceptionResponse(), webRequest);

        return new ResponseEntity<>(errorAttributes, exception.getExceptionResponse().getHttpStatus());
    }
}
