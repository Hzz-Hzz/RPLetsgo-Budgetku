package xyz.rpletsgo.workspace.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkspaceFactoryTest {
    
    @Test
    void create() {
        var factory = new WorkspaceFactory();
        var workspace = factory.create("a");
        
        assertEquals("a", workspace.getNama());
        assertEquals(1, workspace.getKategoriPemasukan().size());
        assertEquals(1, workspace.getSpendingAllowances().size());
    }
}