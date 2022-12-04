package xyz.rpletsgo.pengeluaran.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.common.repository.FinancialEventRepository;
import xyz.rpletsgo.pengeluaran.exceptions.FinancialEventNotFoundException;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.pengeluaran.repository.PengeluaranRepository;
import xyz.rpletsgo.tagihan.model.Tagihan;
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
    @Autowired
    FinancialEventRepository financialEventRepository;

    public List<FinancialEvent> getPengeluaransByWorkspace(String workspaceId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getPengeluarans();
    }

    public Pengeluaran getPengeluaranById(String workspaceId, String pengeluaranId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.existFinancialEventOrThrow(pengeluaranId);
        return pengeluaranRepository.findById(pengeluaranId).orElse(null);
    }

    public void create(String workspaceId, String nama, String keterangan, LocalDateTime waktu, long nominal, String spendingAllowanceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var sumberDana = workspace.getSpendingAllowanceOrThrow(spendingAllowanceId);

        Tagihan tagihanYangDibayar = null;
        if(tagihanId != null) {
            workspace.existFinancialEventOrThrow(tagihanId);
            tagihanYangDibayar = tagihanRepository.findById(tagihanId).orElse(null);
        }

        var pengeluaran = new Pengeluaran(nama, keterangan, waktu, sumberDana, tagihanYangDibayar);
        pengeluaran.setNominal(nominal);

        pengeluaranRepository.saveAndFlush(pengeluaran);
        workspace.addFinancialEvent(pengeluaran);
        workspaceRepository.save(workspace);
    }

    public void update(String workspaceId, String pengeluaranId, String nama, String keterangan, LocalDateTime waktu, long nominal) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);

        workspace.existFinancialEventOrThrow(pengeluaranId);
        var pengeluaran = pengeluaranRepository.findById(pengeluaranId).orElseThrow(
                () -> new FinancialEventNotFoundException("Pengeluaran with id " + pengeluaranId + " not found")
        );

        pengeluaran.valueUpdate(nama, keterangan, waktu, nominal);

        workspace.deleteFinancialEventOrThrow(pengeluaranId);
        workspace.addFinancialEvent(pengeluaran);

        workspaceRepository.save(workspace);
    }

    public void delete(String workspaceId, String pengeluaranId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.existFinancialEventOrThrow(pengeluaranId);
        var pengeluaran = pengeluaranRepository.findById(pengeluaranId).orElseThrow(
                () -> new FinancialEventNotFoundException("Pengeluaran with id " + pengeluaranId + " not found")
        );
        pengeluaran.valueUpdate(null, null, null, 0);

        workspace.deleteFinancialEventOrThrow(pengeluaranId);
        pengeluaranRepository.deleteById(pengeluaranId);
        workspaceRepository.saveAndFlush(workspace);
    }
}
