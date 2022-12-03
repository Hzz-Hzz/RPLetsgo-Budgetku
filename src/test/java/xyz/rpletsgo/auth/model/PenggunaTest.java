package xyz.rpletsgo.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.auth.exceptions.UnauthorizedAccessException;
import xyz.rpletsgo.common.core.IPengguna;
import xyz.rpletsgo.workspace.model.Workspace;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PenggunaTest {
    static String username = "a";
    static String password = "b";
    static String name = "c";
    static String email = "d";
    
    
    Workspace workspace1;
    Workspace workspace2;
    Workspace workspace3;
    Workspace workspace4;
    String workspaceId1 = "a";
    String workspaceId2 = "b";
    String workspaceId3 = "c";
    String workspaceId4 = "d";
    
    @BeforeEach
    void setup(){
        workspace1 = mock(Workspace.class);
        workspace2 = mock(Workspace.class);
        workspace3 = mock(Workspace.class);
        workspace4 = mock(Workspace.class);
        
        when(workspace1.getId()).thenReturn(workspaceId1);
        when(workspace2.getId()).thenReturn(workspaceId2);
        when(workspace3.getId()).thenReturn(workspaceId3);
        when(workspace4.getId()).thenReturn(workspaceId4);
    }
    
    @Test
    void constructorIsCorrect(){
        IPengguna pengguna = new Pengguna(username, password, email);
        
        assertEquals(username, pengguna.getUsername());
        assertEquals(password, pengguna.getPassword());
        assertEquals(email, pengguna.getEmail());
    }
    
    @Test
    void setterGetterRunsCorrectly(){
        IPengguna pengguna = new Pengguna("", "", "");
        
        pengguna.setPassword(password);
        pengguna.setEmail(email);
        
        assertEquals(password, pengguna.getPassword());
        assertEquals(email, pengguna.getEmail());
    }
    
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(() -> new Pengguna());
    }
    
    
    
    
    @Test
    void getWorkspace_shouldThrowIfPenggunaHasNoAccessToIt(){
        var pengguna = new Pengguna("", "", "");
        pengguna.addToCreatedWorkspaces(workspace1);
        pengguna.addToCreatedWorkspaces(workspace2);
        pengguna.joinWorkspace(workspace3);
        pengguna.joinWorkspace(workspace4);
        
        assertNull(
            pengguna.getWorkspace("z")
        );
    }
    
    @Test
    void getWorkspace_retrieveWorkspaceFromCreatedWorkspaces(){
        var pengguna = new Pengguna("", "", "");
        pengguna.addToCreatedWorkspaces(workspace1);
        pengguna.addToCreatedWorkspaces(workspace2);
        pengguna.joinWorkspace(workspace3);
        pengguna.joinWorkspace(workspace4);
        
        var res = pengguna.getWorkspace(workspaceId2);
        assertSame(workspace2, res);
    }
    
    @Test
    void getWorkspace_retrieveWorkspaceFromJoinedWorkspace(){
        var pengguna = new Pengguna("", "", "");
        pengguna.addToCreatedWorkspaces(workspace1);
        pengguna.addToCreatedWorkspaces(workspace2);
        pengguna.joinWorkspace(workspace3);
        pengguna.joinWorkspace(workspace4);
        
        var res = pengguna.getWorkspace(workspaceId4);
        assertSame(workspace4, res);
    }
    
    
    
    
    
    @Test
    void getWorkspaceIfAuthorizedOrThrow_shouldThrowIfPenggunaHasNoAccessToIt(){
        var pengguna = new Pengguna("", "", "");
        pengguna.addToCreatedWorkspaces(workspace1);
        pengguna.addToCreatedWorkspaces(workspace2);
        pengguna.joinWorkspace(workspace3);
        pengguna.joinWorkspace(workspace4);
        
        assertThrows(
            UnauthorizedAccessException.class,
            () -> pengguna.getWorkspaceIfAuthorizedOrThrow("z")
        );
    }
    
    @Test
    void getWorkspaceIfAuthorizedOrThrow_retrieveWorkspaceFromCreatedWorkspaces(){
        var pengguna = new Pengguna("", "", "");
        pengguna.addToCreatedWorkspaces(workspace1);
        pengguna.addToCreatedWorkspaces(workspace2);
        pengguna.joinWorkspace(workspace3);
        pengguna.joinWorkspace(workspace4);
        
        var res = pengguna.getWorkspaceIfAuthorizedOrThrow(workspaceId2);
        assertSame(workspace2, res);
    }
    
    @Test
    void getWorkspaceIfAuthorizedOrThrow_retrieveWorkspaceFromJoinedWorkspace(){
        var pengguna = new Pengguna("", "", "");
        pengguna.addToCreatedWorkspaces(workspace1);
        pengguna.addToCreatedWorkspaces(workspace2);
        pengguna.joinWorkspace(workspace3);
        pengguna.joinWorkspace(workspace4);
        
        var res = pengguna.getWorkspaceIfAuthorizedOrThrow(workspaceId4);
        assertSame(workspace4, res);
    }
}