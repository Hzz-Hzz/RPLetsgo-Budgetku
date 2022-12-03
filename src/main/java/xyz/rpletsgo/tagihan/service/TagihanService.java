package xyz.rpletsgo.tagihan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.tagihan.model.TagihanFactory;
import xyz.rpletsgo.tagihan.repository.TagihanRepository;
import xyz.rpletsgo.workspace.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TagihanService {
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    @Autowired
    WorkspaceRepository workspaceRepository;
    TagihanFactory tagihanFactory = new TagihanFactory();
    @Autowired
    TagihanRepository tagihanRepository;

    public void create(String workspaceId, String nama, String keterangan, LocalDateTime waktu, long nominal){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        var tagihan = tagihanFactory.create(waktu);
        tagihan.updateValue(nama, keterangan, waktu, nominal);

        workspace.addFinancialEvent(tagihan);
        tagihanRepository.save(tagihan);
        workspaceRepository.save(workspace);
    }

    public void createAuto(String workspaceId, String tagihanId, String newName, String newKeterangan, LocalDateTime newWaktu, long newNominal, int intervalId) {

    }
    
    public void update(String workspaceId, String tagihanId, String newName, String newKeterangan, LocalDateTime newWaktu, long newNominal){
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.existFinancialEventOrThrow(tagihanId);
        var tagihan = tagihanRepository.findById(tagihanId).orElseThrow();
        tagihan.updateValue(newName, newKeterangan, newWaktu, newNominal);
        tagihanRepository.save(tagihan);
    }

    public void deleteTagihan(String workspaceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.deleteFinancialEventOrThrow(tagihanId);
        tagihanRepository.deleteById(tagihanId);
    }

    public List<FinancialEvent> getListTagihan(String workspaceId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        return workspace.getTagihan();
    }

    public FinancialEvent getTagihan(String workspaceId, String tagihanId) {
        var workspace = loggedInPengguna.authorizeWorkspace(workspaceId);
        workspace.existFinancialEventOrThrow(tagihanId);
        var tagihan = tagihanRepository.findById(tagihanId).orElseThrow();
        return tagihan;
    }


}
