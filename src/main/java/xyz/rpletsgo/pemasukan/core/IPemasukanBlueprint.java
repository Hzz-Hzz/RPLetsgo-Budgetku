package xyz.rpletsgo.pemasukan.core;

import xyz.rpletsgo.common.core.IFinancialEventBlueprint;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

public interface IPemasukanBlueprint extends IFinancialEventBlueprint {
    KategoriPemasukan getKategoriPemasukan();
    
}
