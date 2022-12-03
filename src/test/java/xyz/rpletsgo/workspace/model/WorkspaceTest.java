package xyz.rpletsgo.workspace.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.core.AutomaticFinancialEvent;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkspaceTest {
    
    @Test
    void addFinancialEvent() {
        FinancialEvent financialEvent1 = mock(FinancialEvent.class);
        FinancialEvent financialEvent2 = mock(FinancialEvent.class);
        
        IWorkspace workspace = new Workspace();
        workspace.addFinancialEvent(financialEvent1);
        workspace.addFinancialEvent(financialEvent2);
        
        List<FinancialEvent> financialEventList = workspace.getFinancialEvents();
        assertSame(financialEvent1, financialEventList.get(0));
        assertSame(financialEvent2, financialEventList.get(1));
    }
    
    
    @Test
    void addFinancialEvents() {
        FinancialEvent financialEvent1 = mock(FinancialEvent.class);
        FinancialEvent financialEvent2 = mock(FinancialEvent.class);
        
        IWorkspace workspace = new Workspace();
        workspace.addFinancialEvents(
            List.of(financialEvent1, financialEvent2)
        );
        
        List<FinancialEvent> financialEventList = workspace.getFinancialEvents();
        assertSame(financialEvent1, financialEventList.get(0));
        assertSame(financialEvent2, financialEventList.get(1));
    }
    
    
    
    
    @Test
    void triggerAutomation(){
        var automaticFinancialEvent = mock(AutomaticFinancialEvent.class);
    
        var workspace = new Workspace();
        workspace.automaticFinancialEvent = automaticFinancialEvent;
        workspace.triggerAutomation();
        
        verify(automaticFinancialEvent, times(1)).triggerEventCreation(workspace);
    }
    
    
    @Test
    void initialize_itShouldInitializeAutomaticFinancialEvent() {
        var workspace = new Workspace();
        workspace.initialize();
        
        assertNotNull(workspace.getAutomaticFinancialEvent());
    }
    
    @Test
    void initialize_itShouldCreateInitialKategoriAndSpendingAllowanceWithCorrectConnectionBetweenThem(){
        var workspace = new Workspace();
        workspace.initialize();
        
        var spendingAllowanceList = workspace.getSpendingAllowances();
        var kategoriPemasukanList = workspace.getKategoriPemasukan();
        
        assertEquals(1, spendingAllowanceList.size());
        assertEquals(1, kategoriPemasukanList.size());
        
        var spendingAllowance = spendingAllowanceList.get(0);
        var kategoriPemasukan = kategoriPemasukanList.get(0);
        var alokasiSpendingAllowance = kategoriPemasukan.getAlokasiSpendingAllowances();
        
        assertEquals(1, alokasiSpendingAllowance.size());
        assertEquals(1.0, alokasiSpendingAllowance.get(0).getBesarAlokasi());
        assertSame(spendingAllowance, alokasiSpendingAllowance.get(0).getSpendingAllowance());
    }
}