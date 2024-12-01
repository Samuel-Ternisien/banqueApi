package tp.imt.banque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdvisorNotFoundException.class)
    public ResponseEntity<String> handleAdvisorNotFoundException(AdvisorNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdvisorNotSpecifiedException.class)
    public ResponseEntity<String> handleAdvisorNotSpecifiedException(AdvisorNotSpecifiedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
