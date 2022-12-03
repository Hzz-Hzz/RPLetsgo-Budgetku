package xyz.rpletsgo.common.exceptions;

import org.springframework.http.HttpStatus;

public class GeneralException extends AutomaticallyHandledException{
    public GeneralException(String message, HttpStatus status) {
        super(message, status);
    }
}
