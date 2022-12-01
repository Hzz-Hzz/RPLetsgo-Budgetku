package xyz.rpletsgo.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class AutomaticallyHandledException extends RuntimeException {
    @Getter
    final HttpStatus status;
    
    protected AutomaticallyHandledException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
