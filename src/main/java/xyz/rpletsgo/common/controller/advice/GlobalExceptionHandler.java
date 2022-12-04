package xyz.rpletsgo.common.controller.advice;

import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

@Generated
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(AutomaticallyHandledException e) {
        return new ResponseEntity<>(
            e.getClass().getSimpleName() + ": " + e.getMessage(), e.getStatus()
        );
    }
}
