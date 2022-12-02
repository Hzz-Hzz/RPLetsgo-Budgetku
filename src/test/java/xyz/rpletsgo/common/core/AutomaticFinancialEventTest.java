package xyz.rpletsgo.common.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.common.model.FinancialEventCreationSchedule;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class AutomaticFinancialEventTest {
    AutomaticFinancialEvent automaticFinancialEvent;
    
    @BeforeEach
    void setup(){
        automaticFinancialEvent = new AutomaticFinancialEvent();
    }
    
    @Test
    void constructor_shouldBeInitializedWithZeroSchedule() {
        assertEquals(0, automaticFinancialEvent.schedules.size());
    }
    
    @Test
    void addNewSchedule() {
        var schedule = mock(FinancialEventCreationSchedule.class);
        automaticFinancialEvent.addNewSchedule(schedule);
        assertSame(schedule, automaticFinancialEvent.schedules.get(0));
    }
    
    @Test
    void triggerEventCreation() {
        var schedule1 = mock(FinancialEventCreationSchedule.class);
        var schedule2 = mock(FinancialEventCreationSchedule.class);
        
        var event1 = mock(FinancialEvent.class);
        var event2 = mock(FinancialEvent.class);
        var event3 = mock(FinancialEvent.class);
        var listEvent12 = List.of(event1, event2);
        var listEvent3 = List.of(event3);
        
        when(schedule1.triggerCreation()).thenReturn(listEvent12);
        when(schedule2.triggerCreation()).thenReturn(listEvent3);
    
        var workspace = mock(IWorkspace.class);
        automaticFinancialEvent.addNewSchedule(schedule1);
        automaticFinancialEvent.addNewSchedule(schedule2);
        automaticFinancialEvent.triggerEventCreation(workspace);
        
        verify(workspace, times(1)).addFinancialEvents(listEvent12);
        verify(workspace, times(1)).addFinancialEvents(listEvent3);
    }
}