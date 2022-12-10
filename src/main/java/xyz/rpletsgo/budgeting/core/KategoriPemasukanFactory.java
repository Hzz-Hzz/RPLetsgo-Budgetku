package xyz.rpletsgo.budgeting.core;


import xyz.rpletsgo.budgeting.model.KategoriPemasukan;

public class KategoriPemasukanFactory implements IKategoriPemasukanFactory {
    
    @Override
    public KategoriPemasukan create(
        String nama
    ){
        var ret = new KategoriPemasukan();
        ret.setNama(nama);
        return ret;
    }
}
