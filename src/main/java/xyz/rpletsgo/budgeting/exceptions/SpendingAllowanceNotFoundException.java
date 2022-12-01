package xyz.rpletsgo.budgeting.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class SpendingAllowanceNotFoundException extends AutomaticallyHandledException {
    public SpendingAllowanceNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
