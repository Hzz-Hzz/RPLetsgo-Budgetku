package xyz.rpletsgo.pengeluaran.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class PengeluaranFactoryTest {
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
    
    static IPengeluaranBlueprint initializeBlueprintMock(Tagihan tagihan){
        IPengeluaranBlueprint blueprint = mock(IPengeluaranBlueprint.class);
        when(blueprint.getNama()).thenReturn(nama);
        when(blueprint.getKeterangan()).thenReturn(keterangan);
        when(blueprint.getNominal()).thenReturn(nominal);
        when(blueprint.getSumberDana()).thenReturn(sumberDana);
        when(blueprint.getTagihanYangDibayar()).thenReturn(tagihan);
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
        IPengeluaranBlueprint blueprint = initializeBlueprintMock(tagihan);
        IWorkspace workspace = mock(IWorkspace.class);
    
        PengeluaranFactory factory = new PengeluaranFactory();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Pengeluaran pengeluaran = factory.create(blueprint, workspace);
    
        assertEquals(nama, pengeluaran.getNama());
        assertEquals(keterangan, pengeluaran.getKeterangan());
        assertEquals(nominal, pengeluaran.getNominal());
        assertSame(sumberDana, pengeluaran.getSumberDana());
        assertSame(tagihan, pengeluaran.getTagihanYangDibayar());
        assertSame(currentTime, pengeluaran.getWaktu());
    
        verify(workspace, times(1)).addFinancialEvent(pengeluaran);
    }
}

