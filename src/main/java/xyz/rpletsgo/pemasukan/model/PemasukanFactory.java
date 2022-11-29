package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pemasukan.core.IPemasukanFactory;
import xyz.rpletsgo.workspace.core.IWorkspace;

@Entity
@Table
public class PemasukanFactory extends FinancialEventFactory implements IPemasukanFactory {
    @Getter
    @Setter
    @ManyToOne
    KategoriPemasukan kategoriPemasukan;
    
    @Override
    public Pemasukan create(IWorkspace workspace) {
        Pemasukan pemasukan = new Pemasukan();
        sideEffect_initialize(workspace, pemasukan);
        pemasukan.setKategori(getKategoriPemasukan());
        return pemasukan;
    }
}
