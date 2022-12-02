package xyz.rpletsgo.auth.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.auth.exceptions.UnauthorizedAccessException;
import xyz.rpletsgo.common.core.IPengguna;
import xyz.rpletsgo.workspace.model.Workspace;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Pengguna implements IPengguna {
    @Getter
    @Id
    @Column(name = "username", updatable = false)
    private String username;
    
    @Getter
    @Setter
    @Column(name = "password")
    private String password;
    
    @Getter
    @Setter
    @Column(name = "email")
    private String email;
    
    @Getter
    @Column(name = "lastLoginDate")
    private LocalDateTime lastLoginDate;
    
    @Getter
    @Setter
    @Cascade({CascadeType.ALL})
    @ManyToMany(fetch = FetchType.EAGER)
    List<Workspace> joinedWorkspaces;
    
    @Getter
    @Setter
    @Cascade({CascadeType.ALL})
    @OneToMany(fetch = FetchType.EAGER)
    List<Workspace> createdWorkspaces;
    
    
    public Pengguna(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    protected Pengguna(){}
    
    
    @Nullable
    public Workspace getWorkspace(String workspaceId){
        for (Workspace joinedWorkspace: joinedWorkspaces) {
            if (joinedWorkspace.getId().equals(workspaceId))
                return joinedWorkspace;
        }
        for (Workspace createdWorkspace: createdWorkspaces) {
            if (createdWorkspace.getId().equals(workspaceId))
                return createdWorkspace;
        }
        return null;
    }
    
    public Workspace getWorkspaceIfAuthorizedOrThrow(String workspaceId){
        var ret = getWorkspace(workspaceId);
        if (ret == null)
            throw new UnauthorizedAccessException("Workspace is not found");
        return ret;
    }
    
    
    
    @Override
    public void joinWorkspace(Workspace workspace) {
        joinedWorkspaces.add(workspace);
    }
    
    @Override
    public void addToCreatedWorkspaces(Workspace workspace) {
        createdWorkspaces.add(workspace);
    }
}