package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.tagihan.core.ITagihanFactory;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
public class TagihanFactory extends FinancialEventFactory implements ITagihanFactory {
    public void set(String nama, String keterangan, long nominal){
        super.set(nama, keterangan, nominal);
    }
    
    @Override
    public Tagihan create() {
        return create(null);
    }
    
    @Override
    public Tagihan create(@Nullable LocalDateTime waktu) {
        Tagihan tagihan = new Tagihan();
        sideEffect_initialize(tagihan, waktu);
        return tagihan;
    }
}
