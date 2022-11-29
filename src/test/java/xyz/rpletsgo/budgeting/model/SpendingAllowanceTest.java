package xyz.rpletsgo.budgeting.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpendingAllowanceTest {
    
    @Test
    void nominalShouldBeZeroInitially(){
        SpendingAllowance spendingAllowance = new SpendingAllowance();
        assertEquals(0, spendingAllowance.getNominal());
    }
    
    @Test
    void increaseNominal() {
        SpendingAllowance spendingAllowance = new SpendingAllowance();
        spendingAllowance.increaseNominal(10);
        assertEquals(10, spendingAllowance.getNominal());
    }
}