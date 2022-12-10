package xyz.rpletsgo.development;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.repository.PenggunaRepository;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.AutomaticFinancialEvent;
import xyz.rpletsgo.workspace.model.Workspace;

import java.util.List;

/**
 * Hanya bertujuan untuk membantu saat proses development.
 * Tidak ada tujuan fungsionalitas fitur apapun
 */
@lombok.Generated
@Service
public class CreateDummyWorkspaces {
    @Autowired
    PenggunaRepository penggunaRepository;
    
    boolean run = false;
    
    @PostConstruct
    void main(){
        if (run){
            Pengguna pengguna = penggunaRepository.findByUsername("a").orElseThrow();
            pengguna.addToCreatedWorkspaces(
                createWorkspace()
            );
            penggunaRepository.save(pengguna);
        }
    }
    
    
    Workspace createWorkspace(){
        Workspace workspace = new Workspace();
        
        var spendingAllowanceDefault = new SpendingAllowance("sp-1", "sp-1-name", 10_000);
        var spendingAllowance2 = new SpendingAllowance("sp-2", "sp-2-name", 5000);
        var spendingAllowance3 = new SpendingAllowance("sp-3", "sp-3-name", 0);
        workspace.addSpendingAllowance(spendingAllowanceDefault);
        workspace.addSpendingAllowance(spendingAllowance2);
        workspace.addSpendingAllowance(spendingAllowance3);
        
        KategoriPemasukan kategoriDefault = new KategoriPemasukan();
        kategoriDefault.setNama("kat-1");
        kategoriDefault.setAlokasiSpendingAllowances(
            List.of(
                new AlokasiSpendingAllowance("alsp-1-1",
                                             spendingAllowanceDefault, 0.75),
                new AlokasiSpendingAllowance("alsp-1-2",
                                             spendingAllowance2, 0.25)
            )
        );
        
        KategoriPemasukan kategoriPemasukan2 = new KategoriPemasukan();
        kategoriPemasukan2.setNama("kat-2");
        kategoriPemasukan2.setAlokasiSpendingAllowances(
            List.of(
                new AlokasiSpendingAllowance("alsp-2-1",
                                             spendingAllowanceDefault, 0.20),
                new AlokasiSpendingAllowance("alsp-2-2",
                                             spendingAllowance2, 0.3),
                new AlokasiSpendingAllowance("alsp-2-3",
                                             spendingAllowance3, 0.5)
                )
        );
    
        workspace.addKategoriPemasukan(kategoriDefault);
        workspace.addKategoriPemasukan(kategoriPemasukan2);
        
        var automaticFinancialEvent = new AutomaticFinancialEvent();
        workspace.setAutomaticFinancialEvent(automaticFinancialEvent);
        
        return workspace;
    }
}
