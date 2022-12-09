package xyz.rpletsgo.auth.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.rpletsgo.auth.exceptions.InvalidCredentialException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CurrentLoggedInPenggunaTest {
    @MockBean
    WorkspaceRepository workspaceRepository;
    String workspaceId = "a";
    Workspace workspace;
    
    @InjectMocks
    CurrentLoggedInPengguna loggedInPengguna;
    
    Pengguna pengguna;
    
    @BeforeEach
    void setup(){
        workspace = mock(Workspace.class);
        workspaceRepository = mock(WorkspaceRepository.class);
        pengguna = mock(Pengguna.class);
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getCurrentPengguna_throwIfNotSet() {
        loggedInPengguna.setCurrentPengguna(null);
        
        assertThrows(InvalidCredentialException.class,
                     () -> loggedInPengguna.getCurrentPengguna());
    }
    
    @Test
    void getCurrentPengguna_returnSuccessfully() {
        loggedInPengguna.setCurrentPengguna(pengguna);
        
        assertSame(pengguna, loggedInPengguna.getCurrentPengguna());
    }
    
    @Test
    void authorizeWorkspace_throwIfNotSet() {
        loggedInPengguna.setCurrentPengguna(null);
        
        assertThrows(InvalidCredentialException.class,
                     () -> loggedInPengguna.authorizeWorkspace(workspaceId));
    }
    
    /**
     * Must return from repository so that the Workspace object returned is fresh
     */
    @Test
    void authorizeWorkspace_returnFromWorkspaceRepository() {
        loggedInPengguna.setCurrentPengguna(pengguna);
        when(workspaceRepository.findById(workspaceId)).thenReturn(Optional.of(workspace));
        
    
        var res = loggedInPengguna.authorizeWorkspace(workspaceId);
    
        verify(pengguna, times(1)).getWorkspaceIfAuthorizedOrThrow(workspaceId);
        assertSame(workspace, res);
    }
}