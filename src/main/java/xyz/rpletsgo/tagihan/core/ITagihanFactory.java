package xyz.rpletsgo.tagihan.core;

import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

public interface ITagihanFactory extends IFinancialEventFactory {
    @Override
    Tagihan create();
    @Override
    Tagihan create(@Nullable LocalDateTime waktu);
}
