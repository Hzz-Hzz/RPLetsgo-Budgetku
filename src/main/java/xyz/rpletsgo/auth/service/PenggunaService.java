package xyz.rpletsgo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.exceptions.InvalidCredentialException;
import xyz.rpletsgo.auth.exceptions.UsernameAlreadyExistsException;
import xyz.rpletsgo.auth.exceptions.UsernameNotFoundException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.repository.PenggunaRepository;
import xyz.rpletsgo.auth.repository.SessionRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

@Service
public class PenggunaService {
    @Autowired
    PenggunaRepository penggunaRepository;
    
    @Autowired
    SessionRepository sessionRepository;
    
    
    
    
    public Pengguna getPengguna(String username){
        return penggunaRepository.findByUsername(username).orElse(null);
    }
    
    
    public Pengguna getPenggunaOrThrow(String username){
        var pengguna = getPengguna(username);
        if (pengguna == null)
            throw new UsernameNotFoundException("Username " + username + " not found.");
        return pengguna;
    }
    
    public String loginPengguna(String username, String password){
        var pengguna = getPenggunaOrThrow(username);
        if (!pengguna.getPassword().equals(password))
            throw new InvalidCredentialException("Wrong password");
        return sessionRepository.createSession(pengguna);
    }
    
    public void registerPengguna(String username, String email, String password){
        var pengguna = getPengguna(username);
        if (pengguna != null)
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
    
        pengguna = new Pengguna(username, password, email);
        penggunaRepository.save(pengguna);
    }
    
    
    
    @Autowired
    WorkspaceRepository workspaceRepository;
    
    public void authorizeWorkspaceAccessOrThrow(String username, String workspaceId){
        var pengguna = penggunaRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(""));
        pengguna.getWorkspaceIfAuthorizedOrThrow(workspaceId);
    }
}
