package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
public class PengeluaranFactory extends FinancialEventFactory implements IPengeluaranFactory {
    public void set(String nama, String keterangan, long nominal, SpendingAllowance sumberDana,
                    Tagihan tagihanYangDibayar){
        super.set(nama, keterangan, nominal);
        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;
    }
    
    @Setter
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    SpendingAllowance sumberDana;
    
    @Setter
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    Tagihan tagihanYangDibayar;
    
    @Override
    public Pengeluaran create() {
        return create(null);
    }
    
    @Override
    public Pengeluaran create(@Nullable LocalDateTime waktu) {
        Pengeluaran pengeluaran = new Pengeluaran();
        sideEffect_initialize(pengeluaran, waktu);
        
        pengeluaran.setSumberDanaTagihanNominal(sumberDana, tagihanYangDibayar, getNominal());
        return pengeluaran;
    }
}
