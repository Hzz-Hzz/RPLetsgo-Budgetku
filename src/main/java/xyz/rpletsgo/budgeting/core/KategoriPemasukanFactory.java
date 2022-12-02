package xyz.rpletsgo.budgeting.core;


import org.springframework.http.HttpStatus;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.exceptions.GeneralException;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.ArrayList;
import java.util.List;

public class KategoriPemasukanFactory implements IKategoriPemasukanFactory {
    
    @Override
    public KategoriPemasukan create(
        String nama,
        List<SpendingAllowance> spendingAllowances,
        List<Double> besarAlokasi
    ){
        var ret = new KategoriPemasukan();
        ret.setNama(nama);
    
        if (spendingAllowances.size() != besarAlokasi.size())
            throw new GeneralException("banyaknya SpendingAllowances harus sama dengan banyaknya besarAlokasi",
                                       HttpStatus.BAD_REQUEST);
    
        var alokasiSpendingAllowances = new ArrayList<AlokasiSpendingAllowance>();
        ret.setAlokasiSpendingAllowances(alokasiSpendingAllowances);
    
    
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
    
        return ret;
    }
}
