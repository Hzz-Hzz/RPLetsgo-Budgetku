package xyz.rpletsgo.workspace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.rpletsgo.workspace.core.IWorkspace;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;


@Service
public class WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepository;
    
    public void createWorkspace(String nama){
        var workspace = new Workspace();
        workspace.setNama(nama);
        workspaceRepository.save(workspace);
    }

    public void updateWorkspace(String workspaceId, String nama){
        var workspace = workspaceRepository.findById(workspaceId).get();
        workspace.setNama(nama);
        workspaceRepository.save(workspace);
    }

    public IWorkspace getWorkspace(String workspaceId){
        return workspaceRepository.findById(workspaceId).get();
    }

    public void deleteWorkspace(String workspaceId){
        workspaceRepository.deleteById(workspaceId);
    }
}
