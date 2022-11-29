package xyz.rpletsgo.pemasukan.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PemasukanBlueprintTest {
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(PemasukanBlueprint::new);
    }
}