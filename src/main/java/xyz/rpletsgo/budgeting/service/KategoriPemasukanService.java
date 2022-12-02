package xyz.rpletsgo.budgeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.core.AlokasiSpendingAllowanceFactory;
import xyz.rpletsgo.budgeting.core.KategoriPemasukanFactory;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.repository.AdditionalSpendingAllowanceRepository;
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
    AdditionalSpendingAllowanceRepository additionalSpendingAllowanceRepository;
    
    KategoriPemasukanFactory kategoriPemasukanFactory = new KategoriPemasukanFactory();
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
        kategoriPemasukanRepository.saveAndFlush(kategoriPemasukan);
        
        workspace.addKategoriPemasukan(kategoriPemasukan);
        workspaceRepository.save(workspace);
        return kategoriPemasukan;
    }


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
    
        workspace.addKategoriPemasukan(kategoriPemasukan);
        kategoriPemasukanRepository.saveAndFlush(kategoriPemasukan);
        return kategoriPemasukan;
    }


    public List<KategoriPemasukan> getKategoriPemasukanByWorkspace(
        String workspaceId
    ){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getKategoriPemasukan();
    }
    
//
//    public void deleteSpendingAllowanceFromWorkspace(
//        String workspaceId, String spendingAllowanceId
//    ){
//        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
//        workspace.removeSpendingAllowance(spendingAllowanceId);
//        workspaceRepository.save(workspace);
//    }
}
