package xyz.rpletsgo.pemasukan.service;

import jakarta.persistence.AttributeOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.repository.KategoriPemasukanRepository;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pemasukan.repository.PemasukanRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;

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

    public void create(String workspaceId, String pemasukanNama, String keterangan, LocalDateTime waktu,
                       Long nominal, String kategoriPemasukanId){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var kategoriPemasukan = kategoriPemasukanRepository.findById(kategoriPemasukanId);

        var pemasukan = new Pemasukan(pemasukanNama, keterangan, waktu, nominal, kategoriPemasukan.orElseThrow());

        kategoriPemasukan.orElseThrow().addPemasukan(pemasukan.getNominal());

        workspace.addFinancialEvent(pemasukan);
        workspaceRepository.save(workspace);
    }

    public void update(String workspaceId, String pemasukanId, String pemasukanNama, String keterangan,
                       LocalDateTime waktu, Long nominal, String kategoriPemasukanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var kategoriPemasukan = kategoriPemasukanRepository.findById(kategoriPemasukanId);

        workspace.existFinancialEventOrThrow(pemasukanId);
        var pemasukan = pemasukanRepository.findById(pemasukanId);

        pemasukan.orElseThrow().valueUpdate(pemasukanNama, keterangan, waktu, nominal, kategoriPemasukan.orElseThrow());
        workspaceRepository.save(workspace);
    }

    public void delete(String workspaceId, String pemasukanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);

    }
}
