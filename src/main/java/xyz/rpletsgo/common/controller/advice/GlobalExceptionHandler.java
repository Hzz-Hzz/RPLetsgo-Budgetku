package xyz.rpletsgo.common.controller.advice;

import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledExceptionDto;

@Generated
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AutomaticallyHandledExceptionDto> handleException(AutomaticallyHandledException e) {
        return new ResponseEntity<>(
            new AutomaticallyHandledExceptionDto(e),
            e.getStatus()
        );
    }
}
