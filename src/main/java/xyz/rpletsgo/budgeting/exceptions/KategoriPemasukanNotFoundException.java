package xyz.rpletsgo.budgeting.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class KategoriPemasukanNotFoundException extends AutomaticallyHandledException {
    public KategoriPemasukanNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
