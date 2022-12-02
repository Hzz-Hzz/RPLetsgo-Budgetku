package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pemasukan.core.IPemasukanFactory;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
public class PemasukanFactory extends FinancialEventFactory implements IPemasukanFactory {
    public void set(String nama, String keterangan, long nominal, KategoriPemasukan kategoriPemasukan){
        super.set(nama, keterangan, nominal);
        this.kategoriPemasukan = kategoriPemasukan;
    }
    
    @Getter
    @Setter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(cascade={CascadeType.REMOVE})
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
