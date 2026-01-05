package fr.diginamic.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({VilleApiException.class})
    protected ResponseEntity<String> handleErrors(VilleApiException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
