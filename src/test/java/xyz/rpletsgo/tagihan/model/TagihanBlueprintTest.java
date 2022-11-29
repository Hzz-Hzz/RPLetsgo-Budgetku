package xyz.rpletsgo.tagihan.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TagihanBlueprintTest {
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(TagihanBlueprint::new);
    }
}