package xyz.rpletsgo.pemasukan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.core.AlokasiSpendingAllowanceFactory;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.common.repository.FinancialEventRepository;
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
    FinancialEventRepository financialEventRepository;
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;
    
    
    @Autowired
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    AlokasiSpendingAllowanceFactory alokasiSpendingAllowanceFactory = new AlokasiSpendingAllowanceFactory();

    public List<FinancialEvent> getPemasukansByWorkspace(String workspaceId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getPemasukans();
    }

    public Pemasukan create(String workspaceId, String pemasukanNama, String keterangan, LocalDateTime waktu,
                       Long nominal, String kategoriPemasukanId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var kategoriPemasukan = kategoriPemasukanRepository.findById(kategoriPemasukanId)
            .orElseThrow(() -> new KategoriPemasukanNotFoundException("kategori not found"));

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
        workspace.existFinancialEventOrThrow(pemasukanId);
    
        var pemasukan = pemasukanRepository.findById(pemasukanId).orElseThrow();
        {
            var oldKategori = pemasukan.getKategori();
            var oldAlokasi = alokasiSpendingAllowanceFactory.create(
                oldKategori.getAlokasiSpendingAllowances(),
                alokasiSpendingAllowanceRepository, spendingAllowanceRepository
            );
        
            oldKategori.setAlokasiSpendingAllowances(oldAlokasi);
            oldKategori.addPemasukan(-1 * pemasukan.getNominal());
            spendingAllowanceRepository.saveAllAndFlush(
                oldKategori.getSpendingAllowanceYangTerkait()
            );
        }
    
        var kategoriPemasukan = kategoriPemasukanRepository.findById(kategoriPemasukanId)
            .orElseThrow(() -> new KategoriPemasukanNotFoundException("kategori not found"));
        kategoriPemasukan.addPemasukan(nominal);
        var alokasiAfter = kategoriPemasukan.getSpendingAllowanceYangTerkait();
        spendingAllowanceRepository.saveAllAndFlush(alokasiAfter);
    
        pemasukan = pemasukanRepository.findById(pemasukanId).orElseThrow();
        pemasukan.valueUpdate(pemasukanNama, keterangan, waktu, nominal, kategoriPemasukan);
        pemasukanRepository.save(pemasukan);
        financialEventRepository.save(pemasukan);
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
