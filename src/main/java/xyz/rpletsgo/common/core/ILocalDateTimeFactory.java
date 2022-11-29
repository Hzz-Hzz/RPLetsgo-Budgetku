package xyz.rpletsgo.common.core;

import java.time.LocalDateTime;

public interface ILocalDateTimeFactory {
    LocalDateTime createLocalDateTimeEpoch(long epochInSecond);
    LocalDateTime createLocalDateTime();
}
