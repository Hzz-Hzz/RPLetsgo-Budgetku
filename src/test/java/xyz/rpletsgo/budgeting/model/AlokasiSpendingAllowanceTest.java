package xyz.rpletsgo.budgeting.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.exceptions.GeneralException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AlokasiSpendingAllowanceTest {
    @Test
    void setBesarAlokasi_throwsIfNegative() {
        var alokasiSpendingAllowance = new AlokasiSpendingAllowance();
        alokasiSpendingAllowance.setBesarAlokasi(0.98);
        
        assertThrows(GeneralException.class,
                     () -> alokasiSpendingAllowance.setBesarAlokasi(-0.01));
        assertEquals(0.98, alokasiSpendingAllowance.getBesarAlokasi());
    }
    @Test
    void setBesarAlokasi_successIfBetweenZeroAndOne() {
        var alokasiSpendingAllowance = new AlokasiSpendingAllowance();
        
        alokasiSpendingAllowance.setBesarAlokasi(0.98);
        assertEquals(0.98, alokasiSpendingAllowance.getBesarAlokasi());
        alokasiSpendingAllowance.setBesarAlokasi(1);
        assertEquals(1, alokasiSpendingAllowance.getBesarAlokasi());
    }
    
    
    @Test
    void increaseNominalSpendingAllowance() {
        SpendingAllowance spendingAllowance = mock(SpendingAllowance.class);
    
        long nominal = 100;
        double alokasi = 1;
        long hasilPembagian = (long) (100*1);
        
        AlokasiSpendingAllowance alokasiSpendingAllowance = new AlokasiSpendingAllowance();
        alokasiSpendingAllowance.setSpendingAllowance(spendingAllowance);
        alokasiSpendingAllowance.setBesarAlokasi(alokasi);
    
        alokasiSpendingAllowance.increaseNominal(nominal);
        
        verify(spendingAllowance, times(1)).increaseNominal(hasilPembagian);
    }
}