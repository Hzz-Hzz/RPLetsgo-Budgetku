package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.tagihan.core.ITagihanFactory;

@Entity
@Table
public class TagihanFactory extends FinancialEventFactory implements ITagihanFactory {
    @Override
    public Tagihan create() {
        Tagihan tagihan = new Tagihan();
        sideEffect_initialize(tagihan);
        return tagihan;
    }
}
