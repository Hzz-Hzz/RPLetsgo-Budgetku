package xyz.rpletsgo.pemasukan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pemasukan.repository.PemasukanRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PemasukanService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    PemasukanRepository pemasukanRepository;
    @Autowired
    KategoriPemasukanRepository kategoriPemasukanRepository;
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;

    public List<FinancialEvent> getPemasukansByWorkspace(String workspaceId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getPemasukans();
    }

    public Pemasukan create(String workspaceId, String pemasukanNama, String keterangan, LocalDateTime waktu,
                       Long nominal, String kategoriPemasukanId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var kategoriPemasukan = kategoriPemasukanRepository.findById(kategoriPemasukanId).orElseThrow();

        var pemasukan = new Pemasukan(pemasukanNama, keterangan, waktu, nominal, kategoriPemasukan);

        kategoriPemasukan.addPemasukan(pemasukan.getNominal());
        spendingAllowanceRepository.saveAllAndFlush(kategoriPemasukan.getSpendingAllowanceYangTerkait());

        workspace.addFinancialEvent(pemasukan);
        pemasukanRepository.saveAndFlush(pemasukan);
        workspaceRepository.save(workspace);
        return pemasukan;
    }

    public Pemasukan update(String workspaceId, String pemasukanId, String pemasukanNama, String keterangan,
                       LocalDateTime waktu, Long nominal, String kategoriPemasukanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var kategoriPemasukan = kategoriPemasukanRepository.findById(kategoriPemasukanId).orElseThrow();

        workspace.existFinancialEventOrThrow(pemasukanId);

        var pemasukan = pemasukanRepository.findById(pemasukanId).orElseThrow();

        pemasukan.getKategori().addPemasukan(-1 * pemasukan.getNominal());
        spendingAllowanceRepository.saveAllAndFlush(pemasukan.getKategori().getSpendingAllowanceYangTerkait());

        kategoriPemasukan.addPemasukan(nominal);
        spendingAllowanceRepository.saveAllAndFlush(kategoriPemasukan.getSpendingAllowanceYangTerkait());

        pemasukan.valueUpdate(pemasukanNama, keterangan, waktu, nominal, kategoriPemasukan);

        pemasukanRepository.saveAndFlush(pemasukan);
        workspaceRepository.save(workspace);
        return pemasukan;
    }

    public Pemasukan delete(String workspaceId, String pemasukanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var pemasukan = pemasukanRepository.findById(pemasukanId).orElseThrow();
        var kategoriPemasukan = pemasukan.getKategori();
        kategoriPemasukan.addPemasukan(-1 * pemasukan.getNominal());

        workspace.deleteFinancialEventOrThrow(pemasukanId);
        pemasukanRepository.deleteById(pemasukanId);
        workspaceRepository.save(workspace);
        return pemasukan;
    }
}
