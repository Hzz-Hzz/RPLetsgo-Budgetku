package xyz.rpletsgo.budgeting.core;

import xyz.rpletsgo.budgeting.model.KategoriPemasukan;

public interface IKategoriPemasukanFactory {
    KategoriPemasukan create(
        String nama
    );
}
