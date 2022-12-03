package xyz.rpletsgo.workspace.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceException;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkspaceSpendingAllowanceTest {
    @Test
    void getSpendingAllowanceOrThrow_shouldThrowIfNotFound(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        when(spendingAllowance1.getId()).thenReturn("a");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        
        assertThrows(SpendingAllowanceNotFoundException.class,
                     () -> workspace.getSpendingAllowanceOrThrow("b"));
    }
    
    @Test
    void getSpendingAllowanceOrThrow_shouldReturnTheRequestedObject(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        when(spendingAllowance1.getId()).thenReturn("a");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        
        assertSame(spendingAllowance1,
                   workspace.getSpendingAllowanceOrThrow("a"));
    }
    
    
    
    
    @Test
    void getSpendingAllowanceOrThrow_shouldThrowIfAnyOfTheListIsNotFound(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        var spendingAllowance2 = mock(SpendingAllowance.class);
        when(spendingAllowance1.getId()).thenReturn("a");
        when(spendingAllowance2.getId()).thenReturn("b");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        workspace.addSpendingAllowance(spendingAllowance2);
        
        var list = List.of("a", "b", "c");
        assertThrows(SpendingAllowanceNotFoundException.class,
                     () -> workspace.getSpendingAllowanceOrThrow(list)
        );
    }
    
    @Test
    void getSpendingAllowanceOrThrow_shouldReturnWithCorrectOrder(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        var spendingAllowance2 = mock(SpendingAllowance.class);
        when(spendingAllowance1.getId()).thenReturn("a");
        when(spendingAllowance2.getId()).thenReturn("b");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        workspace.addSpendingAllowance(spendingAllowance2);
    
        var res1 = workspace.getSpendingAllowanceOrThrow(List.of("a", "b"));
        var res2 = workspace.getSpendingAllowanceOrThrow(List.of("b", "a"));
        
        assertEquals(List.of(spendingAllowance1, spendingAllowance2), res1);
        assertEquals(List.of(spendingAllowance2, spendingAllowance1), res2);
    }
    
    
    
    @Test
    void removeSpendingAllowance_ensureWorkspaceHaveOneKategori(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        when(spendingAllowance1.getId()).thenReturn("a");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        
        assertThrows(SpendingAllowanceException.class,
                     () -> workspace.removeSpendingAllowance("a"));
    }
    @Test
    void removeSpendingAllowance_removeCorrectly(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        var spendingAllowance2 = mock(SpendingAllowance.class);
        var spendingAllowance3 = mock(SpendingAllowance.class);
        
        when(spendingAllowance1.getId()).thenReturn("a");
        when(spendingAllowance2.getId()).thenReturn("b");
        when(spendingAllowance3.getId()).thenReturn("c");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        workspace.addSpendingAllowance(spendingAllowance2);
        workspace.addSpendingAllowance(spendingAllowance3);
        
        workspace.removeSpendingAllowance("b");
        var remainingKategoris = workspace.getSpendingAllowances();
        
        assertEquals(List.of(spendingAllowance1, spendingAllowance3),
                     remainingKategoris);
    }
    
    @Test
    void removeSpendingAllowance_throwsIfNotFound(){
        var spendingAllowance1 = mock(SpendingAllowance.class);
        var spendingAllowance2 = mock(SpendingAllowance.class);
        
        when(spendingAllowance1.getId()).thenReturn("a");
        when(spendingAllowance2.getId()).thenReturn("b");
        
        var workspace = new Workspace();
        workspace.addSpendingAllowance(spendingAllowance1);
        workspace.addSpendingAllowance(spendingAllowance2);
        
        assertThrows(SpendingAllowanceNotFoundException.class,
                     () -> workspace.removeSpendingAllowance("c"));
    }
}