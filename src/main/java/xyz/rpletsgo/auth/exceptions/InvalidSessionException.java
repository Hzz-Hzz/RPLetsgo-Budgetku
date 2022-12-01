package xyz.rpletsgo.auth.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class InvalidSessionException extends AutomaticallyHandledException {
    public InvalidSessionException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
