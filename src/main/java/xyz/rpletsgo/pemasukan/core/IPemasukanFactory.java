package xyz.rpletsgo.pemasukan.core;

import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pemasukan.model.Pemasukan;

public interface IPemasukanFactory extends IFinancialEventFactory {
    KategoriPemasukan getKategoriPemasukan();
    void setKategoriPemasukan(KategoriPemasukan value);
    
    @Override
    Pemasukan create();
}
