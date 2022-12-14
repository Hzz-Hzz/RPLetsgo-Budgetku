package xyz.rpletsgo.budgeting.core;


import org.springframework.http.HttpStatus;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.repository.AlokasiSpendingAllowanceRepository;
import xyz.rpletsgo.budgeting.repository.SpendingAllowanceRepository;
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
    
        if (0.98 > totalAlokasi || totalAlokasi > 1.0){
            throw new GeneralException("totalAlokasi harus di antara 0.98 dan 1", HttpStatus.BAD_REQUEST);
        }
    
        return alokasiSpendingAllowances;
    }
    
    /**
     * Used to get most updated object of AlokasiSpendingAllowance from JPA
     */
    @Override
    public List<AlokasiSpendingAllowance> create(
        List<AlokasiSpendingAllowance> oldAlokasiList,
        AlokasiSpendingAllowanceRepository alokasiRepository,
        SpendingAllowanceRepository spendingAllowanceRepository
    ){
        var ret = new ArrayList<AlokasiSpendingAllowance>();
        
        for (AlokasiSpendingAllowance oldAlokasi: oldAlokasiList) {
            var oldSpendingAllowance = oldAlokasi.getSpendingAllowance();
            
            var oldAlokasiId = oldAlokasi.getId();
            var oldSpendingAllowanceId = oldSpendingAllowance.getId();
            
            var newAlokasi = alokasiRepository.findById(oldAlokasiId)
                .orElseThrow(() -> new GeneralException("Alokasi not found", HttpStatus.BAD_REQUEST));
            var newSpendingAllowance = spendingAllowanceRepository
                .findById(oldSpendingAllowanceId)
                .orElseThrow(() -> new SpendingAllowanceNotFoundException("Spending Allowance Not Found"));
            
            newAlokasi.setSpendingAllowance(newSpendingAllowance);
            ret.add(newAlokasi);
        }
        return ret;
    }
}
