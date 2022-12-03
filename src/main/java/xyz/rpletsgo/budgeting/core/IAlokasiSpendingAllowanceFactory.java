package xyz.rpletsgo.budgeting.core;

import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;

import java.util.List;

public interface IAlokasiSpendingAllowanceFactory {
    List<AlokasiSpendingAllowance> create(
        List<SpendingAllowance> spendingAllowances,
        List<Double> besarAlokasi
    );
}
