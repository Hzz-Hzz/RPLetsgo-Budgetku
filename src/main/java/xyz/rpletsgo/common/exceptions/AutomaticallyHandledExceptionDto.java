package xyz.rpletsgo.common.exceptions;

import lombok.Data;

@Data
public class AutomaticallyHandledExceptionDto {
    String exceptionName;
    String message;
    String statusResponse;
    int statusCode;
    
    public AutomaticallyHandledExceptionDto(AutomaticallyHandledException exception){
        exceptionName = exception.getClass().getSimpleName();
        message = exception.getMessage();
        statusResponse = exception.getStatus().getReasonPhrase();
        statusCode = exception.getStatus().value();
    }
}
