package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

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
        return create(null);
    }
    
    @Override
    public Pengeluaran create(@Nullable LocalDateTime waktu) {
        Pengeluaran pengeluaran = new Pengeluaran();
        sideEffect_initialize(pengeluaran, waktu);
        
        pengeluaran.setSumberDana(getSumberDana());
        pengeluaran.setTagihanYangDibayar(getTagihanYangDibayar());
        return pengeluaran;
    }
}
