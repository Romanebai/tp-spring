package fr.diginamic.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Controller advice.
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    /**
     * Handle errors response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({VilleApiException.class})
    protected ResponseEntity<String> handleErrors(VilleApiException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
