package se.lexicon.peopleapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.peopleapi.exception.AppResourceNotFoundException;
import se.lexicon.peopleapi.model.CustomExceptionResponse;
import se.lexicon.peopleapi.model.ValidationErrorResponse;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@CrossOrigin("*")
public class ApplicationControllerAdvice {

    public static final String VALIDATIONS_FAILED = "One or several validations failed";

    public CustomExceptionResponse build(String message, HttpStatus status, WebRequest request){
        CustomExceptionResponse responseDTO = new CustomExceptionResponse();
        responseDTO.setError(status.name());
        responseDTO.setStatus(status.value());
        responseDTO.setTimeStamp(LocalDateTime.now());
        responseDTO.setPath(request.getDescription(false));
        responseDTO.setMessage(message);
        return responseDTO;
    }

    @ExceptionHandler(AppResourceNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleAppResourceNotFoundException(AppResourceNotFoundException ex, WebRequest request){
        return ResponseEntity.status(404).body(
                build(ex.getMessage(), HttpStatus.NOT_FOUND, request)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        return ResponseEntity.badRequest()
                .body(build(ex.getMessage(), HttpStatus.BAD_REQUEST, request));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CustomExceptionResponse> handleIllegalArgumentException(IllegalStateException ex, WebRequest request){
        return ResponseEntity.badRequest()
                .body(build(ex.getMessage(), HttpStatus.BAD_REQUEST, request));
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<CustomExceptionResponse> handleDateTimeException(DateTimeException ex, WebRequest request){
        return ResponseEntity.badRequest()
                .body(build(ex.getMessage(),HttpStatus.BAD_REQUEST, request));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<CustomExceptionResponse> handleDateTimeParseException(DateTimeParseException ex, WebRequest request){
        return ResponseEntity.badRequest()
                .body(build(ex.getMessage(),HttpStatus.BAD_REQUEST, request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ){
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setError(HttpStatus.BAD_REQUEST.name());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setPath(request.getDescription(false));
        response.setMessage(VALIDATIONS_FAILED);

        List<String> fields = ex.getBindingResult().getFieldErrors().stream()
                .distinct()
                .map(FieldError::getField)
                .collect(Collectors.toList());

        Map<String, List<String>> errors = new HashMap<>();

        for(String field : fields){
            List<String> list = new ArrayList<>();
            for(FieldError fieldError : ex.getFieldErrors(field)){
                list.add(fieldError.getDefaultMessage());
            }
            errors.put(field, list);
        }

        response.setErrors(errors);

        return ResponseEntity.badRequest().body(response);
    }




}
