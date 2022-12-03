package xyz.rpletsgo.budgeting.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.core.AlokasiSpendingAllowanceFactory;
import xyz.rpletsgo.budgeting.core.KategoriPemasukanFactory;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.repository.AdditionalSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class KategoriPemasukanService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;
    
    @Autowired
    KategoriPemasukanRepository kategoriPemasukanRepository;
    @Autowired
    SpendingAllowanceRepository spendingAllowanceRepository;
    @Autowired
    AlokasiSpendingAllowanceRepository alokasiSpendingAllowanceRepository;
    
    @Autowired
    AdditionalSpendingAllowanceRepository additionalSpendingAllowanceRepository;
    
    @Setter
    KategoriPemasukanFactory kategoriPemasukanFactory = new KategoriPemasukanFactory();
    @Setter
    AlokasiSpendingAllowanceFactory alokasiSpendingAllowanceFactory = new AlokasiSpendingAllowanceFactory();

    public KategoriPemasukan create(
        String workspaceId,
        String namaKategoriPemasukan,
        Double[] besarAlokasi,
        String[] spendingAllowanceId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var spendingAllowance = workspace.getSpendingAllowanceOrThrow(
            List.of(spendingAllowanceId));
        
        var alokasi = alokasiSpendingAllowanceFactory.create(
            spendingAllowance, Arrays.asList(besarAlokasi)
        );
        var kategoriPemasukan = kategoriPemasukanFactory.create(
            namaKategoriPemasukan
        );
        
        kategoriPemasukan.setAlokasiSpendingAllowances(alokasi);
        alokasiSpendingAllowanceRepository.saveAllAndFlush(alokasi);
        kategoriPemasukanRepository.saveAndFlush(kategoriPemasukan);
        
        workspace.addKategoriPemasukan(kategoriPemasukan);
        workspaceRepository.save(workspace);
        return kategoriPemasukan;
    }
    
    
    /**
     * Updating besarAlokasi and spendingAllowanceId won't update existing
     * spending allowance
     */
    public KategoriPemasukan update(
        String workspaceId,
        String kategoriPemasukanId,
        String namaKategoriPemasukan,
        Double[] besarAlokasi,
        String[] spendingAllowanceId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var spendingAllowance = workspace.getSpendingAllowanceOrThrow(
            List.of(spendingAllowanceId));
        
        var alokasi = alokasiSpendingAllowanceFactory.create(
            spendingAllowance, Arrays.asList(besarAlokasi)
        );
        var kategoriPemasukan = kategoriPemasukanRepository.findById(
            kategoriPemasukanId
        ).orElseThrow(() -> new KategoriPemasukanNotFoundException("Kategori Pemasukan Not Found"));
    
        kategoriPemasukan.setNama(namaKategoriPemasukan);
        kategoriPemasukan.setAlokasiSpendingAllowances(alokasi);
        
        alokasiSpendingAllowanceRepository.saveAllAndFlush(alokasi);
        kategoriPemasukanRepository.saveAndFlush(kategoriPemasukan);
        return kategoriPemasukan;
    }


    public List<KategoriPemasukan> getKategoriPemasukanByWorkspace(
        String workspaceId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getKategoriPemasukan();
    }
    
    
    /**
     * Delete a KategoriPemasukan from workspace.
     * Note, this method doesn't remove KategoriPemasukan from 'history'.
     */
    public void deleteKategoriPemasukanFromWorkspace(
        String workspaceId, String kategoriPemasukanId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.removeKategoriPemasukan(kategoriPemasukanId);
        workspaceRepository.save(workspace);
    }
}
