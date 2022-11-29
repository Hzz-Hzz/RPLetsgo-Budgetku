package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEventBlueprint;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranBlueprint;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

@Entity
@Table
public class PengeluaranBlueprint extends FinancialEventBlueprint implements IPengeluaranBlueprint {
    @Setter
    @Getter
    @ManyToOne
    SpendingAllowance sumberDana;
    
    @Setter
    @Getter
    @ManyToOne
    Tagihan tagihanYangDibayar;
    
    @Override
    public Pengeluaran create(IWorkspace workspace) {
        Pengeluaran pengeluaran = new Pengeluaran();
        sideEffect_initialize(workspace, pengeluaran);
        
        pengeluaran.setSumberDana(getSumberDana());
        pengeluaran.setTagihanYangDibayar(getTagihanYangDibayar());
        return pengeluaran;
    }
}
