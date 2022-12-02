package xyz.rpletsgo.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.repository.PenggunaRepository;
import xyz.rpletsgo.workspace.core.IWorkspace;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.List;


@Service
public class WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    PenggunaRepository penggunaRepository;
    
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    
    public Workspace createWorkspace(String nama){
        var workspace = new Workspace();
        workspace.initialize();
        workspace.setNama(nama);
        workspaceRepository.save(workspace);
    
        var pengguna = loggedInPengguna.getCurrentPengguna();
        pengguna.addToCreatedWorkspaces(workspace);
        penggunaRepository.save(pengguna);
        return workspace;
    }

    public void updateWorkspace(String workspaceId, String nama){
        var workspace = workspaceRepository.findById(workspaceId).get();
        workspace.setNama(nama);
        workspaceRepository.save(workspace);
    }

    public IWorkspace getWorkspace(String workspaceId){
        return workspaceRepository.findById(workspaceId).get();
    }

    public List<Workspace> getWorkspaces(){
        return loggedInPengguna.getCurrentPengguna().getCreatedWorkspaces();
    }

    public void deleteWorkspace(String workspaceId){
        workspaceRepository.deleteById(workspaceId);
    }
}
