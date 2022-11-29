package xyz.rpletsgo.common.core;

import java.time.LocalDateTime;

public class LocalDateTimeFactory implements ILocalDateTimeFactory{
    @Override
    public LocalDateTime createLocalDateTime() {
        return LocalDateTime.now();
    }
}
