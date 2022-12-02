package xyz.rpletsgo.auth.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class InvalidCredentialException extends AutomaticallyHandledException {
    public InvalidCredentialException(String message){
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
