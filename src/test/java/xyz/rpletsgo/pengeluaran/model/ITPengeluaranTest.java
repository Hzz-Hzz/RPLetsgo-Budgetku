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
        pengeluaran.setTagihanYangDibayar(tagihan1);
        pengeluaran.setSumberDana(spendingAllowance1);
        pengeluaran.setNominal(besarPengeluaran);
        
        assertEquals(initialNominal - besarPengeluaran, tagihan1.getNominal());
        assertEquals(initialNominal - besarPengeluaran, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
    
    
    
    @Test
    void setSumberDanaTagihanNominal_correctlyGiveOutput(){
        pengeluaran.setNominal(besarPengeluaran);
    
        assertEquals(initialNominal - besarPengeluaran, tagihan1.getNominal());
        assertEquals(initialNominal - besarPengeluaran, spendingAllowance1.getNominal());
        assertEquals(initialNominal, tagihan2.getNominal());
        assertEquals(initialNominal, spendingAllowance2.getNominal());
    }
}