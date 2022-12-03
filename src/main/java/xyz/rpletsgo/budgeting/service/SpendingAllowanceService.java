package xyz.rpletsgo.budgeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.List;

@Service
public class SpendingAllowanceService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;
    
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;
    
    
    public SpendingAllowance create(String workspaceId, String spendingAllowanceName){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var spendingAllowance = new SpendingAllowance(spendingAllowanceName, 0);
        workspace.addSpendingAllowance(spendingAllowance);
    
        spendingAllowanceRepository.saveAndFlush(spendingAllowance);
        workspaceRepository.save(workspace);
        return spendingAllowance;
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
    }
}
