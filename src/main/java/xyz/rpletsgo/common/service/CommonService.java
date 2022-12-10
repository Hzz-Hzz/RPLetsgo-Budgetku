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

    private long getSaldo(long pemasukan, long pengeluaran) {
        return pemasukan - pengeluaran;
    }

    public Map<String, String> getTotalFinancial(String workspaceId){
        Map<String, String> financialTotalSummary = new HashMap<>();
        long pengeluaran = getTotalPengeluaran(workspaceId);
        long pemasukan = getTotalPemasukan(workspaceId);
        long tagihan = getTotalTagihan(workspaceId);
        long saldo = getSaldo(pemasukan, pengeluaran);
        financialTotalSummary.put("pengeluaran", Long.toString(pengeluaran));
        financialTotalSummary.put("pemasukan", Long.toString(pemasukan));
        financialTotalSummary.put("tagihan", Long.toString(tagihan));
        financialTotalSummary.put("saldo", Long.toString(saldo));
        return financialTotalSummary;
    }
}
