package xyz.rpletsgo.auth.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class UnauthorizedAccessException extends AutomaticallyHandledException {
    public UnauthorizedAccessException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
