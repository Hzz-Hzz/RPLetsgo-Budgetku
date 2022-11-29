package xyz.rpletsgo.pengeluaran.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranBlueprint;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PengeluaranBlueprintTest {
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(PengeluaranBlueprint::new);
    }
    
    static String nama = "a";
    static String keterangan = "b";
    static long nominal = 1;
    static SpendingAllowance sumberDana = mock(SpendingAllowance.class);
    
    static LocalDateTime currentTime;
    static ILocalDateTimeFactory dateTimeFactory;
    
    @BeforeEach
    void setup(){
        currentTime = LocalDateTime.now();
        dateTimeFactory = mock(ILocalDateTimeFactory.class);
        when(dateTimeFactory.createLocalDateTime()).thenReturn(currentTime);
    }
    
    static IPengeluaranBlueprint initializeBlueprint(Tagihan tagihan){
        IPengeluaranBlueprint blueprint = new PengeluaranBlueprint();
        blueprint.setNama(nama);
        blueprint.setKeterangan(keterangan);
        blueprint.setNominal(nominal);
        blueprint.setSumberDana(sumberDana);
        blueprint.setTagihanYangDibayar(tagihan);
        return blueprint;
    }
    
    @Test
    void create_nonNullTagihan() {
        Tagihan tagihan = mock(Tagihan.class);
        verifyCreateWorkingCorrectly(tagihan);
    }
    
    @Test
    void create_nullTagihan() {
        verifyCreateWorkingCorrectly(null);
    }
    
    static void verifyCreateWorkingCorrectly(Tagihan tagihan){
        IWorkspace workspace = mock(IWorkspace.class);
        
        IPengeluaranBlueprint factory = initializeBlueprint(tagihan);
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Pengeluaran pengeluaran = factory.create(workspace);
        
        assertEquals(nama, pengeluaran.getNama());
        assertEquals(keterangan, pengeluaran.getKeterangan());
        assertEquals(nominal, pengeluaran.getNominal());
        assertSame(sumberDana, pengeluaran.getSumberDana());
        assertSame(tagihan, pengeluaran.getTagihanYangDibayar());
        assertSame(currentTime, pengeluaran.getWaktu());
        
        verify(workspace, times(1)).addFinancialEvent(pengeluaran);
    }
}