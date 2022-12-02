package xyz.rpletsgo.pemasukan.core;

import org.springframework.lang.Nullable;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.pemasukan.model.Pemasukan;

import java.time.LocalDateTime;

public interface IPemasukanFactory extends IFinancialEventFactory {
    KategoriPemasukan getKategoriPemasukan();
    void setKategoriPemasukan(KategoriPemasukan value);
    
    
    @Override
    Pemasukan create();
    @Override
    Pemasukan create(@Nullable LocalDateTime waktu);
}
