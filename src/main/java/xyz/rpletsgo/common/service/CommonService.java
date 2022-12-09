package xyz.rpletsgo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.List;

@Service
public class CommonService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;

    public List<FinancialEvent> getWorkspaceFinancialEvents(String workspaceId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getFinancialEvents();
    }
}
