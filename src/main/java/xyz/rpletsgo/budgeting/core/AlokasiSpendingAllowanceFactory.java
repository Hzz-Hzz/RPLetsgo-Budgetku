package xyz.rpletsgo.budgeting.core;


import org.springframework.http.HttpStatus;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.exceptions.GeneralException;

import java.util.ArrayList;
import java.util.List;

public class AlokasiSpendingAllowanceFactory implements IAlokasiSpendingAllowanceFactory {
    @Override
    public List<AlokasiSpendingAllowance> create(
        List<SpendingAllowance> spendingAllowances,
        List<Double> besarAlokasi
    ){
        if (spendingAllowances.size() != besarAlokasi.size())
            throw new GeneralException("banyaknya SpendingAllowances harus sama dengan banyaknya besarAlokasi",
                                       HttpStatus.BAD_REQUEST);
    
        var alokasiSpendingAllowances = new ArrayList<AlokasiSpendingAllowance>();
        double totalAlokasi = 0;
        
        for (int i = 0; i < spendingAllowances.size(); i++) {
            var spendingAllowance = spendingAllowances.get(i);
            var alokasi = besarAlokasi.get(i);
            totalAlokasi += alokasi;
        
            alokasiSpendingAllowances.add(
                new AlokasiSpendingAllowance(null, spendingAllowance, alokasi)
            );
        }
    
        if (0 > totalAlokasi || totalAlokasi > 100){
            throw new GeneralException("totalAlokasi harus di antara 0 dan 100", HttpStatus.BAD_REQUEST);
        }
    
        return alokasiSpendingAllowances;
    }
}
