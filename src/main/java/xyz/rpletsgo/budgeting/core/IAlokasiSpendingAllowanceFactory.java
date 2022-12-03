package xyz.rpletsgo.budgeting.core;

import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;

import java.util.List;

public interface IAlokasiSpendingAllowanceFactory {
    List<AlokasiSpendingAllowance> create(
        List<SpendingAllowance> spendingAllowances,
        List<Double> besarAlokasi
    );
    
    
    List<AlokasiSpendingAllowance> create(
        List<AlokasiSpendingAllowance> oldAlokasi,
        AlokasiSpendingAllowanceRepository alokasiRepository,
        SpendingAllowanceRepository spendingAllowanceRepository
    );
}
