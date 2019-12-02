package singlepagespringboot.controllers.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String NOT_FOUND_RESPONSE = "not found";

    @ExceptionHandler({FileNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(FileNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(NOT_FOUND_RESPONSE, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
