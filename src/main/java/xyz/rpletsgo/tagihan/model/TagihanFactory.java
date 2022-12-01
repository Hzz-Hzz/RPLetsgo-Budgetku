package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.tagihan.core.ITagihanFactory;

import java.time.LocalDateTime;

@Entity
@Table
public class TagihanFactory extends FinancialEventFactory implements ITagihanFactory {
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
