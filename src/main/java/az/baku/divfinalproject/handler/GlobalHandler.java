package az.baku.divfinalproject.handler;

import az.baku.divfinalproject.exception.ApplicationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandler extends DefaultErrorAttributes {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handler(ApplicationException exception,
                                                      WebRequest webRequest) {
        return of(exception, webRequest);
    }

    private Map<String, Object> errorAttributes(HttpStatus httpStatus, String message, WebRequest webRequest) {

        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        errorAttributes.put("error", message);
        errorAttributes.put("status", httpStatus.value());
        errorAttributes.put("path", ((ServletRequestAttributes)webRequest).getRequest().getServletPath());

        return errorAttributes;
    }


    private ResponseEntity<Map<String, Object>> of (ApplicationException exception, WebRequest webRequest) {

        Map<String, Object> errorAttributes  = errorAttributes(exception.getExceptionResponse().getHttpStatus(), exception.getMessage(), webRequest);

        return new ResponseEntity<>(errorAttributes, exception.getExceptionResponse().getHttpStatus());
    }

    private ResponseEntity<Map<String, Object>> of (Exception exception, WebRequest webRequest) {

        Map<String, Object> errorAttributes  = errorAttributes(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), webRequest);

        return new ResponseEntity<>(errorAttributes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
