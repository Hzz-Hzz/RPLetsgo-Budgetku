package xyz.rpletsgo.budgeting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class SpendingAllowanceServiceTest {
    
    @MockBean
    CurrentLoggedInPengguna loggedInPengguna;
    @MockBean
    WorkspaceRepository workspaceRepository;
    @MockBean
    SpendingAllowanceRepository spendingAllowanceRepository;
    
    @InjectMocks
    SpendingAllowanceService spendingAllowanceService;
    
    Workspace workspace;
    SpendingAllowance spendingAllowance;
    
    @BeforeEach
    void setup(){
        loggedInPengguna = mock(CurrentLoggedInPengguna.class);
        workspaceRepository = mock(WorkspaceRepository.class);
        spendingAllowanceRepository = mock(SpendingAllowanceRepository.class);
    
        workspace = mock(Workspace.class);
        spendingAllowance = mock(SpendingAllowance.class);
        
        MockitoAnnotations.openMocks(this);
    }
    
    
    String workspaceId = "a";
    String spendingAllowanceName = "name-sp-1";
    String spendingAllowanceId = "sp-1";
    
    @Test
    void create() {
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        
        var res = spendingAllowanceService.create(workspaceId, spendingAllowanceName);
        
        var spendingAllowanceCaptor = ArgumentCaptor.forClass(SpendingAllowance.class);
        verify(workspace).addSpendingAllowance(spendingAllowanceCaptor.capture());
        
        var spendingAllowance = spendingAllowanceCaptor.getValue();
        assertSame(spendingAllowance, res);
        assertEquals(spendingAllowanceName, spendingAllowance.getNama());
    
        verify(spendingAllowanceRepository, times(1)).saveAndFlush(spendingAllowance);
        verify(workspaceRepository, times(1)).save(workspace);
    }
    
    @Test
    void update() {
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        when(workspace.getSpendingAllowanceOrThrow(spendingAllowanceId)).thenReturn(spendingAllowance);
    
        spendingAllowanceService.update(workspaceId, spendingAllowanceId, spendingAllowanceName);
    
        verify(spendingAllowance, times(1)).setNama(spendingAllowanceName);
        verify(spendingAllowanceRepository, times(1)).save(spendingAllowance);
    }
    
    @Test
    void getSpendingAllowancesByWorkspace() {
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        var spendingAllowanceList = List.of(spendingAllowance);
        when(workspace.getSpendingAllowances()).thenReturn(spendingAllowanceList);
        
        var res = spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
        
        assertEquals(spendingAllowanceList, res);
    }
    
    @Test
    void deleteSpendingAllowanceFromWorkspace() {
        when(loggedInPengguna.authorizeWorkspace(workspaceId)).thenReturn(workspace);
        
    
        spendingAllowanceService.deleteSpendingAllowanceFromWorkspace(workspaceId, spendingAllowanceId);
    
        verify(workspace, times(1)).removeSpendingAllowance(spendingAllowanceId);
        verify(workspaceRepository, times(1)).save(workspace);
    }
}