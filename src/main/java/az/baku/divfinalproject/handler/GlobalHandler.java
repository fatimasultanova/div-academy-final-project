package az.baku.divfinalproject.handler;

import az.baku.divfinalproject.dto.response.ExceptionResponse;
import az.baku.divfinalproject.exception.ApplicationException;
import az.baku.divfinalproject.exception.ExceptionEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import az.baku.divfinalproject.dto.response.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandler extends DefaultErrorAttributes {
    private final Logger logger = LoggerFactory.getLogger(GlobalHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException ex) {
        ExceptionResponse exceptionResponse = ex.getExceptionResponse();
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        logger.error(ex.getMessage());
        List<FieldError> fieldErrors = result.getFieldErrors();
        String fields = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.joining(". "));
        return new ErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), fields);
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
