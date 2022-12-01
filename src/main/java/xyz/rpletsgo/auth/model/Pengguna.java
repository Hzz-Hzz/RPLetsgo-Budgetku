package xyz.rpletsgo.auth.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    List<Workspace> joinedWorkspaces;
    
    @Getter
    @Setter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    List<Workspace> createdWorkspaces;
    
    
    
    public Pengguna(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    protected Pengguna(){}
    
    
    
    @Override
    public void joinWorkspace(Workspace workspace) {
        joinedWorkspaces.add(workspace);
    }
    
    @Override
    public void addToCreatedWorkspaces(Workspace workspace) {
        createdWorkspaces.add(workspace);
    }
}