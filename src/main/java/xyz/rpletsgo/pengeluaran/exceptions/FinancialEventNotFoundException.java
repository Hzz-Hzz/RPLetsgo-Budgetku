package xyz.rpletsgo.pengeluaran.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class FinancialEventNotFoundException extends AutomaticallyHandledException {
    public FinancialEventNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
