package xyz.rpletsgo.workspace.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.repository.PenggunaRepository;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.workspace.core.IWorkspace;
import xyz.rpletsgo.workspace.core.WorkspaceFactory;
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
    KategoriPemasukanRepository kategoriPemasukanRepository;
    @Autowired
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;
    
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    
    @Setter
    WorkspaceFactory workspaceFactory = new WorkspaceFactory();
    
    public Workspace createWorkspace(String nama){
        var workspace = workspaceFactory.create(nama);
        
        spendingAllowanceRepository.saveAllAndFlush(workspace.getSpendingAllowances());
        alokasiSpendingAllowanceRepository.saveAllAndFlush(workspace.getAlokasiSpendingAllowances());
        kategoriPemasukanRepository.saveAllAndFlush(workspace.getKategoriPemasukan());
        
        workspaceRepository.save(workspace);
    
        var pengguna = loggedInPengguna.getCurrentPengguna();
        pengguna.addToCreatedWorkspaces(workspace);
        penggunaRepository.save(pengguna);
        return workspace;
    }

    public void updateWorkspace(String workspaceId, String nama){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.setNama(nama);
        workspaceRepository.save(workspace);
    }

    public IWorkspace getWorkspace(String workspaceId){
        return loggedInPengguna.authorizeWorkspace(workspaceId);
    }

    public List<Workspace> getWorkspaces(){
        return loggedInPengguna.getCurrentPengguna().getCreatedWorkspaces();
    }

    public void deleteWorkspace(String workspaceId){
        loggedInPengguna.authorizeWorkspace(workspaceId);
        workspaceRepository.deleteById(workspaceId);
    }
}
