package xyz.rpletsgo.auth.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class UsernameNotFoundException extends AutomaticallyHandledException {
    public UsernameNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
