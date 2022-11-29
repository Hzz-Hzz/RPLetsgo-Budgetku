package xyz.rpletsgo.pemasukan.core;

import xyz.rpletsgo.common.core.IFinancialEventBlueprint;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.workspace.core.IWorkspace;

public interface IPemasukanBlueprint extends IFinancialEventBlueprint {
    KategoriPemasukan getKategoriPemasukan();
    void setKategoriPemasukan(KategoriPemasukan value);
    
    @Override
    Pemasukan create(IWorkspace workspace);
}
