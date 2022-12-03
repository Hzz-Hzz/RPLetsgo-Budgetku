package xyz.rpletsgo.workspace.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pengeluaran.exceptions.FinancialEventNotFoundException;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkspaceFinancialEventTest {
    Pengeluaran pengeluaran1 = mock(Pengeluaran.class);
    Pengeluaran pengeluaran2 = mock(Pengeluaran.class);
    Pemasukan pemasukan1 = mock(Pemasukan.class);
    Pemasukan pemasukan2 = mock(Pemasukan.class);
    Tagihan tagihan1 = mock(Tagihan.class);
    Tagihan tagihan2 = mock(Tagihan.class);
    
    String pengeluaranId1 = "a";
    String pengeluaranId2 = "b";
    String pemasukanId1 = "c";
    String pemasukanId2 = "d";
    String tagihanId1 = "e";
    String tagihanId2 = "e";
    
    Workspace workspace;
    
    
    @BeforeEach
    void setup(){
        when(pengeluaran1.getId()).thenReturn(pengeluaranId1);
        when(pengeluaran2.getId()).thenReturn(pengeluaranId2);
        when(pemasukan1.getId()).thenReturn(pemasukanId1);
        when(pemasukan2.getId()).thenReturn(pemasukanId2);
        when(tagihan1.getId()).thenReturn(tagihanId1);
        when(tagihan2.getId()).thenReturn(tagihanId2);
        
        workspace = new Workspace();
        workspace.addFinancialEvents(List.of(pemasukan1, pengeluaran1, tagihan1, pemasukan2, pengeluaran2, tagihan2));
    }
    
    @Test
    void existFinancialEventOrThrow(){
        workspace.existFinancialEventOrThrow(pengeluaranId1);
        workspace.existFinancialEventOrThrow(pemasukanId2);
        workspace.existFinancialEventOrThrow(pemasukanId1);
        workspace.existFinancialEventOrThrow(pemasukanId2);
        workspace.existFinancialEventOrThrow(tagihanId1);
        workspace.existFinancialEventOrThrow(tagihanId2);
        
        assertThrows(FinancialEventNotFoundException.class,
                     () -> workspace.existFinancialEventOrThrow("z"));
    }
    
    @Test
    void getPemasukans(){
        assertEquals(List.of(pemasukan1, pemasukan2), workspace.getPemasukans());
    }
    
    @Test
    void getPengeluarans(){
        assertEquals(List.of(pengeluaran1, pengeluaran2), workspace.getPengeluarans());
    }
    
    @Test
    void getTagihans(){
        assertEquals(List.of(tagihan1, tagihan2), workspace.getTagihan());
    }
    
    @Test
    void deleteFinancialEventOrThrow_throwsIfNotFound(){
        assertThrows(FinancialEventNotFoundException.class,
                     () -> workspace.deleteFinancialEventOrThrow("z"));
    }
    
    @Test
    void deleteFinancialEventOrThrow_shouldRemoveSuccessfully(){
        workspace.deleteFinancialEventOrThrow(pemasukanId2);
        
        var res = workspace.getFinancialEvents();
        assertEquals(List.of(pemasukan1, pengeluaran1, tagihan1, pengeluaran2, tagihan2), res);
    }
}