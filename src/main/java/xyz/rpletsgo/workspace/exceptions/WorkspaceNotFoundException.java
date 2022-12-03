package xyz.rpletsgo.workspace.exceptions;

import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.AutomaticallyHandledException;

public class WorkspaceNotFoundException extends AutomaticallyHandledException {
    public WorkspaceNotFoundException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
