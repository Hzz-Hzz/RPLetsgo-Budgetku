package xyz.rpletsgo.auth.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class UsernameAlreadyExistsException extends AutomaticallyHandledException {
    public UsernameAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
