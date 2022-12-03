package xyz.rpletsgo.budgeting.core;

import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

public interface IKategoriPemasukanFactory {
    KategoriPemasukan create(
        String nama
    );
}
