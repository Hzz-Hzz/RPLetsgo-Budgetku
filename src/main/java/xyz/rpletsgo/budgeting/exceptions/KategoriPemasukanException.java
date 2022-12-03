package xyz.rpletsgo.budgeting.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class KategoriPemasukanException extends AutomaticallyHandledException {
    public KategoriPemasukanException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
