package xyz.rpletsgo.common.core;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalDateTimeFactoryTest {
    
    @Test
    void createLocalDateTime() {
        ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
        
        var currentLocalDateTime = LocalDateTime.now().plusSeconds(10);
        var createdLocalDateTime = localDateTimeFactory.createLocalDateTime();
        assertTrue(createdLocalDateTime.compareTo(currentLocalDateTime) < 0);
    }
}