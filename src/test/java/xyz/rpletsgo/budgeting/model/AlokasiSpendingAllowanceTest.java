package xyz.rpletsgo.budgeting.model;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AlokasiSpendingAllowanceTest {
    
    @Test
    void increaseNominalSpendingAllowance() {
        SpendingAllowance spendingAllowance = mock(SpendingAllowance.class);
    
        long nominal = 100;
        double alokasi = 0.25;
        long hasilPembagian = (long) (100*0.25);
        
        AlokasiSpendingAllowance alokasiSpendingAllowance = new AlokasiSpendingAllowance();
        alokasiSpendingAllowance.setSpendingAllowance(spendingAllowance);
        alokasiSpendingAllowance.setBesarAlokasi(alokasi);
    
        alokasiSpendingAllowance.increaseNominal(nominal);
        
        verify(spendingAllowance, times(1)).increaseNominal(hasilPembagian);
    }
}