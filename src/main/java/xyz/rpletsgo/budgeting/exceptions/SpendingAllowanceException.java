package xyz.rpletsgo.budgeting.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class SpendingAllowanceException extends AutomaticallyHandledException {
    public SpendingAllowanceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
