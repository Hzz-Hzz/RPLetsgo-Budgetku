package xyz.rpletsgo.pengeluaran.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.tagihan.model.Tagihan;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ITPengeluaranTest {
    SpendingAllowance spendingAllowance1;
    SpendingAllowance spendingAllowance2;
    Tagihan tagihan1;
    Tagihan tagihan2;
    Pengeluaran pengeluaran;
    
    long initialNominal = 10_000;
    long besarPengeluaran = 2000;
    
    @BeforeEach
    void setup(){
        spendingAllowance1 = new SpendingAllowance(null, null, initialNominal);
        spendingAllowance2 = new SpendingAllowance(null, null, initialNominal);
        tagihan1 = new Tagihan();
        tagihan2 = new Tagihan();
        
        tagihan1.increaseNominal(initialNominal);
        tagihan2.increaseNominal(initialNominal);
    
        pengeluaran = new Pengeluaran();
        pengeluaran.setSumberDanaTagihanNominal(spendingAllowance1, tagihan1, besarPengeluaran);
        
        assertEquals(initialNominal - besarPengeluaran, tagihan1.getNominal());
        assertEquals(initialNominal - besarPengeluaran, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
    
    
    
    @Test
    void setSumberDanaTagihanNominal_correctlyUndo(){
        pengeluaran.setSumberDanaTagihanNominal(null, null, besarPengeluaran);
    
        assertEquals(initialNominal, tagihan1.getNominal());
        assertEquals(initialNominal, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
    
    @Test
    void setSumberDanaTagihanNominal_correctlyReassign(){
        pengeluaran.setSumberDanaTagihanNominal(spendingAllowance2, tagihan2, besarPengeluaran);
        
        assertEquals(initialNominal, tagihan1.getNominal());
        assertEquals(initialNominal, spendingAllowance1.getNominal());
        assertEquals(initialNominal - besarPengeluaran, tagihan2.getNominal());
        assertEquals(initialNominal - besarPengeluaran, spendingAllowance2.getNominal());
    }
    
    @Test
    void setSumberDanaTagihanNominal_handleIfPreviouslyNull(){
        pengeluaran.setSumberDanaTagihanNominal(null, null, besarPengeluaran);
        pengeluaran.setSumberDanaTagihanNominal(spendingAllowance1, tagihan1, besarPengeluaran);
    
        assertEquals(initialNominal - besarPengeluaran, tagihan1.getNominal());
        assertEquals(initialNominal - besarPengeluaran, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
    @Test
    void setSumberDanaTagihanNominal_shouldNotBeAffectedIfCalledMultipleTimes(){
        pengeluaran.setSumberDanaTagihanNominal(spendingAllowance1, tagihan1, besarPengeluaran);
        pengeluaran.setSumberDanaTagihanNominal(spendingAllowance1, tagihan1, besarPengeluaran);
        pengeluaran.setSumberDanaTagihanNominal(spendingAllowance1, tagihan1, besarPengeluaran);
    
        assertEquals(initialNominal - besarPengeluaran, tagihan1.getNominal());
        assertEquals(initialNominal - besarPengeluaran, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
    
    @Test
    void setNominal(){
        long newBesaranPengeluaran = 3000;
        pengeluaran.setNominal(newBesaranPengeluaran);
    
        assertEquals(initialNominal - newBesaranPengeluaran, tagihan1.getNominal());
        assertEquals(initialNominal - newBesaranPengeluaran, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
}