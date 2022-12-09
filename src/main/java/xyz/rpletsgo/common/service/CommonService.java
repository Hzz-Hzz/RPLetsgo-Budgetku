package xyz.rpletsgo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private long getTotalPemasukan(String workspaceId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        List<FinancialEvent> pemasukanList = workspace.getPemasukans();
        long totalPemasukan = 0;
        for(FinancialEvent pemasukan : pemasukanList){
            totalPemasukan += pemasukan.getNominal();
        }
        return totalPemasukan;
    }

    private long getTotalPengeluaran(String workspaceId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        List<FinancialEvent> pengeluaranList = workspace.getPengeluarans();
        long totalPengeluaran = 0;
        for(FinancialEvent pengeluaran : pengeluaranList){
            totalPengeluaran += pengeluaran.getNominal();
        }
        return totalPengeluaran;
    }

    private long getTotalTagihan(String workspaceId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        List<FinancialEvent> tagihanList = workspace.getTagihan();
        long totalTagihan = 0;
        for(FinancialEvent tagihan : tagihanList){
            totalTagihan += tagihan.getNominal();
        }
        return totalTagihan;
    }

    public Map<String, String> getTotalFinancial(String workspaceId){
        Map<String, String> financialTotalSummary = new HashMap<>();
        String pengeluaran = Long.toString(getTotalPengeluaran(workspaceId));
        String pemasukan = Long.toString(getTotalPemasukan(workspaceId));
        String tagihan = Long.toString(getTotalTagihan(workspaceId));
        financialTotalSummary.put("pengeluaran", pengeluaran);
        financialTotalSummary.put("pemasukan", pemasukan);
        financialTotalSummary.put("tagihan", tagihan);
        return financialTotalSummary;
    }
}
