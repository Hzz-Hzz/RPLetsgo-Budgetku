package xyz.rpletsgo.pengeluaran.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.pengeluaran.repository.PengeluaranRepository;
import xyz.rpletsgo.tagihan.repository.TagihanRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PengeluaranService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;
    @Autowired
    TagihanRepository tagihanRepository;
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    @Autowired
    PengeluaranRepository pengeluaranRepository;

    public List<FinancialEvent> getPengeluaransByWorkspace(String workspaceId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getPengeluarans();
    }

    public void create(String workspaceId, String nama, String keterangan, LocalDateTime waktu, long nominal, String spendingAllowanceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var sumberDana = workspace.getSpendingAllowanceOrThrow(spendingAllowanceId);

        workspace.existFinancialEventOrThrow(tagihanId);
        var tagihanYangDibayar = tagihanRepository.findById(tagihanId).orElse(null);

        var pengeluaran = new Pengeluaran(nama, keterangan, waktu, nominal, sumberDana, tagihanYangDibayar);
        pengeluaran.setSumberDanaTagihanNominal(sumberDana, tagihanYangDibayar, nominal);

        workspace.addFinancialEvent(pengeluaran);
        workspaceRepository.save(workspace);
    }

    public void update(String workspaceId, String pengeluaranId, String nama, String keterangan, LocalDateTime waktu, long nominal, String spendingAllowanceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var sumberDana = workspace.getSpendingAllowanceOrThrow(spendingAllowanceId);

        workspace.existFinancialEventOrThrow(tagihanId);
        var tagihanYangDibayar = tagihanRepository.findById(tagihanId).orElse(null);

        workspace.existFinancialEventOrThrow(pengeluaranId);
        var pengeluaran = pengeluaranRepository.findById(pengeluaranId).orElse(null);

        pengeluaran.valueUpdate(nama, keterangan, waktu, nominal, sumberDana, tagihanYangDibayar);
        workspaceRepository.save(workspace);
    }

    public void delete(String workspaceId, String pengeluaranId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.deleteFinancialEventOrThrow(pengeluaranId);
        pengeluaranRepository.deleteById(pengeluaranId);
    }

}