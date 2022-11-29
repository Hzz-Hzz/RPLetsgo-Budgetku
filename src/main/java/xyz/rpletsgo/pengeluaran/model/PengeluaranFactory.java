package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;

@Entity
@Table
public class PengeluaranFactory extends FinancialEventFactory implements IPengeluaranFactory {
    @Setter
    @Getter
    @ManyToOne
    SpendingAllowance sumberDana;
    
    @Setter
    @Getter
    @ManyToOne
    Tagihan tagihanYangDibayar;
    
    @Override
    public Pengeluaran create() {
        Pengeluaran pengeluaran = new Pengeluaran();
        sideEffect_initialize(pengeluaran);
        
        pengeluaran.setSumberDana(getSumberDana());
        pengeluaran.setTagihanYangDibayar(getTagihanYangDibayar());
        return pengeluaran;
    }
}
