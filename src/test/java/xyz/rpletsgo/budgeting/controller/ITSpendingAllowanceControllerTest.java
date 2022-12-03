package xyz.rpletsgo.budgeting.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

import static org.mockito.Mockito.*;


class ITSpendingAllowanceControllerTest {
    @MockBean
    SpendingAllowanceService spendingAllowanceService;
    
    @InjectMocks
    SpendingAllowanceController spendingAllowanceController;
    
    @BeforeEach
    void setup(){
        spendingAllowanceService = mock(SpendingAllowanceService.class);
        
        MockitoAnnotations.openMocks(this);
    }
    
    
    String workspaceId = "a";
    String spendingAllowanceId = "b";
    String spendingAllowanceName = "c";
    
    @Test
    @SneakyThrows
    void createSpendingAllowance() {
        spendingAllowanceController.createSpendingAllowance(workspaceId, spendingAllowanceName);
        verify(spendingAllowanceService, times(1)).create(workspaceId, spendingAllowanceName);
    }
    
    @Test
    void updateSpendingAllowance() {
        spendingAllowanceController.updateSpendingAllowance(workspaceId, spendingAllowanceId, spendingAllowanceName);
        verify(spendingAllowanceService, times(1))
            .update(workspaceId, spendingAllowanceId, spendingAllowanceName);
    }
    
    @Test
    void getSpendingAllowance() {
        spendingAllowanceController.getSpendingAllowance(workspaceId);
        verify(spendingAllowanceService, times(1))
            .getSpendingAllowancesByWorkspace(workspaceId);
    }
    
    @Test
    void deleteSpendingAllowanceFromWorkspace() {
        spendingAllowanceController.deleteSpendingAllowanceFromWorkspace(workspaceId, spendingAllowanceId);
        verify(spendingAllowanceService, times(1))
            .deleteSpendingAllowanceFromWorkspace(workspaceId, spendingAllowanceId);
    }
}