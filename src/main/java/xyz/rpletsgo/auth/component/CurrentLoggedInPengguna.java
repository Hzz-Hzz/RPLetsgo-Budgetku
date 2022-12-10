package xyz.rpletsgo.auth.component;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import xyz.rpletsgo.auth.exceptions.InvalidCredentialException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentLoggedInPengguna {
    @Setter
    private Pengguna currentPengguna;
    
    @Autowired
    WorkspaceRepository workspaceRepository;
    
    public boolean isLoggedIn(){
        return currentPengguna != null;
    }
    
    public Pengguna getCurrentPengguna() {
        if (!isLoggedIn())
            throw new InvalidCredentialException("Not logged in");
        return currentPengguna;
    }
    
    public Workspace authorizeWorkspace(String workspaceId){
        getCurrentPengguna().getWorkspaceIfAuthorizedOrThrow(workspaceId);
        return workspaceRepository.findById(workspaceId).orElseThrow();
    }
}
