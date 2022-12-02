package xyz.rpletsgo.auth.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.workspace.model.Workspace;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentLoggedInPengguna {
    @Getter
    @Setter
    private Pengguna currentPengguna;
    
    public Workspace authorizeWorkspace(String workspaceId){
        return currentPengguna.getWorkspaceIfAuthorizedOrThrow(workspaceId);
    }
}
