package xyz.rpletsgo.budgeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

@Service
public class KategoriPemasukanService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;
    
    @Autowired
    KategoriPemasukanRepository kategoriPemasukanRepository;
    
    
    /*
    public void create(String workspaceId,
                       String namaKategoriPemasukan,
                       ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var spendingAllowance = new KategoriPemasukan("", namaKategoriPemasukan, 0);
        workspace.addSpendingAllowance(spendingAllowance);
    
        workspaceRepository.save(workspace);
    }
    
    
    public void update(String workspaceId, String spendingAllowanceId, String newName){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var spendingAllowance = workspace.getSpendingAllowanceOrThrow(spendingAllowanceId);
        spendingAllowance.setNama(newName);
        
        spendingAllowanceRepository.save(spendingAllowance);
    }
    
    
    public List<SpendingAllowance> getSpendingAllowancesByWorkspace(
        String workspaceId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getSpendingAllowances();
    }
    
    public void deleteSpendingAllowanceFromWorkspace(
        String workspaceId, String spendingAllowanceId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.removeSpendingAllowance(spendingAllowanceId);
        workspaceRepository.save(workspace);
    }*/
}
