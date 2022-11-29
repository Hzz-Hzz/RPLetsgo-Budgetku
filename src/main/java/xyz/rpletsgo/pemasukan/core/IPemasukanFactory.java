package xyz.rpletsgo.pemasukan.core;

import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.workspace.core.IWorkspace;

public interface IPemasukanFactory extends IFinancialEventFactory {
    KategoriPemasukan getKategoriPemasukan();
    void setKategoriPemasukan(KategoriPemasukan value);
    
    @Override
    Pemasukan create(IWorkspace workspace);
}
