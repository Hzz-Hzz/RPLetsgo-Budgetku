package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pemasukan.core.IPemasukanFactory;

import java.time.LocalDateTime;

@Entity
@Table
public class PemasukanFactory extends FinancialEventFactory implements IPemasukanFactory {
    @Getter
    @Setter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST})
    KategoriPemasukan kategoriPemasukan;
    
    @Override
    public Pemasukan create(){
        return create(null);
    }
    
    @Override
    public Pemasukan create(@Nullable LocalDateTime waktu) {
        Pemasukan pemasukan = new Pemasukan();
        sideEffect_initialize(pemasukan, waktu);
        pemasukan.setKategori(getKategoriPemasukan());
        return pemasukan;
    }
}
