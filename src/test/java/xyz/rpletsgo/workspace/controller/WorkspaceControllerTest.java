package xyz.rpletsgo.workspace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.workspace.service.WorkspaceService;

import static org.mockito.Mockito.*;

class WorkspaceControllerTest {
    
    @MockBean
    WorkspaceService workspaceService;
    
    @InjectMocks
    WorkspaceController workspaceController;
    
    @BeforeEach
    void setup(){
        workspaceService = mock(WorkspaceService.class);
        MockitoAnnotations.openMocks(this);
    }
    
    String workspaceId = "a";
    String workspaceName = "b";
    
    @Test
    void createWorkspace() {
        workspaceController.createWorkspace(workspaceName);
        verify(workspaceService, times(1)).createWorkspace(workspaceName);
    }
    
    @Test
    void updateWorkspace() {
        workspaceController.updateWorkspace(workspaceId, workspaceName);
        verify(workspaceService, times(1)).updateWorkspace(workspaceId, workspaceName);
    }
    
    @Test
    void getWorkspaceSingle() {
        workspaceController.getWorkspace(workspaceId);
        verify(workspaceService, times(1)).getWorkspace(workspaceId);
    }
    
    @Test
    void deleteWorkspace() {
        workspaceController.deleteWorkspace(workspaceId);
        verify(workspaceService, times(1)).deleteWorkspace(workspaceId);
    }
}