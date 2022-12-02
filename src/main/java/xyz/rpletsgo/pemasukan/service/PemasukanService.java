package xyz.rpletsgo.pemasukan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
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

    public void create(String workspaceId, String pemasukanNama, String keterangan, LocalDateTime waktu,
                       Long nominal, KategoriPemasukan kategoriPemasukan){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var pemasukan = new Pemasukan(pemasukanNama, keterangan, waktu, nominal, kategoriPemasukan);
    }
}
