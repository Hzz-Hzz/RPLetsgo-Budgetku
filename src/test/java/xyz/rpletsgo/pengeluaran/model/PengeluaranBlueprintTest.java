package xyz.rpletsgo.pengeluaran.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PengeluaranBlueprintTest {
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(PengeluaranBlueprint::new);
    }
}