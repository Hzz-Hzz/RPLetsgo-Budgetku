package xyz.rpletsgo.pengeluaran.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.pengeluaran.model.PengeluaranFactory;
import xyz.rpletsgo.tagihan.repository.TagihanRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;


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

    public void create(String workspaceId, String nama, String keterangan, LocalDateTime waktu, long nominal, String spendingAllowanceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var sumberDana = workspace.getSpendingAllowanceOrThrow(spendingAllowanceId);
        sumberDana.increaseNominal(-nominal);

        workspace.existFinancialEventOrThrow(tagihanId);
        var tagihanYangDibayar = tagihanRepository.findById(tagihanId).orElse(null);

        var pengeluaran = new Pengeluaran(null, nama, keterangan, waktu, nominal, sumberDana, tagihanYangDibayar);
        workspace.addFinancialEvent(pengeluaran);

        workspaceRepository.save(workspace);
    }

    public void autoCreate(String workspaceId, String nama, String keterangan, LocalDateTime waktu, long nominal, String spendingAllowanceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var sumberDana = workspace.getSpendingAllowanceOrThrow(spendingAllowanceId);
        sumberDana.increaseNominal(-nominal);

        workspace.existFinancialEventOrThrow(tagihanId);
        var tagihanYangDibayar = tagihanRepository.findById(tagihanId).orElse(null);

        var pengeluaran = new Pengeluaran(null, nama, keterangan, waktu, nominal, sumberDana, tagihanYangDibayar);
        workspace.addFinancialEvent(pengeluaran);

        workspaceRepository.save(workspace);
    }

}
