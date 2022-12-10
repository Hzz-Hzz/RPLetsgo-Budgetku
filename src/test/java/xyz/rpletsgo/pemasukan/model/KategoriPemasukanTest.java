package xyz.rpletsgo.pemasukan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.AlokasiSpendingAllowance;
import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class KategoriPemasukanTest {
    AlokasiSpendingAllowance alokasiSpendingAllowance1;
    AlokasiSpendingAllowance alokasiSpendingAllowance2;
    SpendingAllowance spendingAllowance1;
    SpendingAllowance spendingAllowance2;
    
    @BeforeEach
    void setup(){
        alokasiSpendingAllowance1 = mock(AlokasiSpendingAllowance.class);
        alokasiSpendingAllowance2 = mock(AlokasiSpendingAllowance.class);
        
        spendingAllowance1 = mock(SpendingAllowance.class);
        spendingAllowance2 = mock(SpendingAllowance.class);
        
        when(alokasiSpendingAllowance1.getSpendingAllowance()).thenReturn(spendingAllowance1);
        when(alokasiSpendingAllowance2.getSpendingAllowance()).thenReturn(spendingAllowance2);
        
        when(alokasiSpendingAllowance1.getBesarAlokasi()).thenReturn(0.75);
        when(alokasiSpendingAllowance2.getBesarAlokasi()).thenReturn(0.25);
    }
    
    
    @Test
    void getSpendingAllowanceYangTerkait_shouldReturnEmptyIfAlokasiSpendingAllowanceIsEmpty() {
        KategoriPemasukan kategoriPemasukan = new KategoriPemasukan();
        assertEquals(0, kategoriPemasukan.getSpendingAllowanceYangTerkait().size());
    }
    
    @Test
    void getSpendingAllowanceYangTerkait() {
        KategoriPemasukan kategoriPemasukan = new KategoriPemasukan();
        kategoriPemasukan.setAlokasiSpendingAllowances(
                List.of(alokasiSpendingAllowance1, alokasiSpendingAllowance2)
        );
        
        List<SpendingAllowance> expected = List.of(spendingAllowance1, spendingAllowance2);
        List<SpendingAllowance> spendingAllowances = kategoriPemasukan.getSpendingAllowanceYangTerkait();
        
        assertTrue(expected.containsAll(spendingAllowances));
    }
    
    @Test
    void addPemasukan() {
        KategoriPemasukan kategoriPemasukan = new KategoriPemasukan();
        kategoriPemasukan.setAlokasiSpendingAllowances(
                List.of(alokasiSpendingAllowance1, alokasiSpendingAllowance2)
        );
    
        long nominal = 100;
        kategoriPemasukan.addPemasukan(nominal);
    
        verify(alokasiSpendingAllowance1, times(1)).increaseNominal(nominal);
        verify(alokasiSpendingAllowance2, times(1)).increaseNominal(nominal);
    }
}