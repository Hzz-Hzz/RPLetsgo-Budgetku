package xyz.rpletsgo.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FinancialEventCreationScheduleTest {
    static long timeInterval = 60;
    static FinancialEventCreationSchedule financialEventCreationSchedule;
    static FinancialEventFactory financialEventFactory;
    static FinancialEvent financialEvent1;
    static FinancialEvent financialEvent2;
    
    static ILocalDateTimeFactory localDateTimeFactory;
    static LocalDateTime now = LocalDateTime.of(2000, 1, 1, 1, 1);
    
    @BeforeEach
    void setup(){
        localDateTimeFactory = mock(LocalDateTimeFactory.class);
        
        financialEvent1 = mock(FinancialEvent.class);
        financialEvent2 = mock(FinancialEvent.class);
        financialEventFactory = mock(FinancialEventFactory.class);
        
        when(financialEventFactory.create())
            .thenReturn(financialEvent1)
            .thenReturn(financialEvent2);
        when(financialEventFactory.create(any()))
            .thenReturn(financialEvent1)
            .thenReturn(financialEvent2);
    }
    
    @Test
    void setToNextCreationDate() {
        financialEventCreationSchedule = new FinancialEventCreationSchedule(
            timeInterval, now, financialEventFactory
        );
        financialEventCreationSchedule.setLocalDateTimeFactory(localDateTimeFactory);
        financialEventCreationSchedule.setToNextCreationDate();
        
        assertEquals(
            now.plusSeconds(timeInterval),
            financialEventCreationSchedule.getNextCreationDate()
        );
    }
    
    @Test
    void triggerCreation_notYet() {
        var res = triggerCreation(1);
        assertEquals(0, res.size());
    }
    
    @Test
    void triggerCreation_oneTime() {
        var res = triggerCreation(0);
        assertEquals(1, res.size());
        assertEquals(financialEvent1, res.get(0));
    }
    
    @Test
    void triggerCreation_twoTime() {
        var res = triggerCreation(-timeInterval);
        assertEquals(2, res.size());
        assertEquals(financialEvent1, res.get(0));
        assertEquals(financialEvent2, res.get(1));
    }
    
    List<FinancialEvent> triggerCreation(long nextCreationDateRelativeToCurrentTime){
        when(localDateTimeFactory.createLocalDateTime()).thenReturn(now);
    
        financialEventCreationSchedule = new FinancialEventCreationSchedule(
            timeInterval, now.plusSeconds(nextCreationDateRelativeToCurrentTime), financialEventFactory
        );
        financialEventCreationSchedule.setLocalDateTimeFactory(localDateTimeFactory);
        return financialEventCreationSchedule.triggerCreation();
    }
}