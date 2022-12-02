package xyz.rpletsgo.budgeting.core;

import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.List;

public interface IKategoriPemasukanFactory {
    KategoriPemasukan create(
        String nama,
        List<SpendingAllowance> spendingAllowances,
        List<Double> besarAlokasi
    );
}
