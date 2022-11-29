package xyz.rpletsgo.workspace.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

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
}