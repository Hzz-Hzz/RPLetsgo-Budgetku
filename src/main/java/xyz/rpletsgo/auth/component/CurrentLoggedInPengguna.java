package xyz.rpletsgo.auth.component;

import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import xyz.rpletsgo.auth.exceptions.InvalidCredentialException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.workspace.model.Workspace;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentLoggedInPengguna {
    @Setter
    private Pengguna currentPengguna;
    
    public Pengguna getCurrentPengguna() {
        if (currentPengguna == null)
            throw new InvalidCredentialException("Not logged in");
        return currentPengguna;
    }
    
    public Workspace authorizeWorkspace(String workspaceId){
        return getCurrentPengguna().getWorkspaceIfAuthorizedOrThrow(workspaceId);
    }
}
