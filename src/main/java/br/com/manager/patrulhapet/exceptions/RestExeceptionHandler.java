package br.com.manager.patrulhapet.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class RestExeceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<RestFieldErrors> fieldErrors = ex.getFieldErrors().stream()
                .map(f -> new RestFieldErrors(
                        f.getObjectName(),
                        f.getField(),
                        f.getDefaultMessage() != null ? f.getDefaultMessage() : f.getCode())
                )
                .toList();

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Values cant be empty",
                fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> eventInvalidType(HttpMessageNotReadableException ex) {
        String message = "Invalid type provided. Please check your input data.";
        String fieldName = "";
        if (ex.getCause() instanceof MismatchedInputException mismatchedInputException) {
            fieldName = mismatchedInputException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst().orElse("Unknown field");
            String expectedType = mismatchedInputException.getTargetType().getSimpleName();

            message = String.format("The field '%s' should be of type '%s'.", fieldName, expectedType);
        }

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                message,
                List.of(new RestFieldErrors("requestBody", fieldName, message))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RestNotFound.class)
    @ResponseBody
    public ResponseEntity<Object> eventNotFound(RestNotFound ex) {

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
