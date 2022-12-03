package xyz.rpletsgo.common.core;

import xyz.rpletsgo.workspace.model.Workspace;

import java.util.List;

public interface IPengguna {
    String getUsername();
    String getPassword();
    String getEmail();
    
    void setPassword(String value);
    void setEmail(String value);
    
    List<Workspace> getCreatedWorkspaces();
    void setCreatedWorkspaces(List<Workspace> workspaces);

    
    List<Workspace> getJoinedWorkspaces();
    void setJoinedWorkspaces(List<Workspace> workspaces);
    
    void joinWorkspace(Workspace workspace);
    void addToCreatedWorkspaces(Workspace workspace);
}
