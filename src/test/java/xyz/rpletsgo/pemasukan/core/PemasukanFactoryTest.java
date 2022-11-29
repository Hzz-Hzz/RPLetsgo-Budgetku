package xyz.rpletsgo.pemasukan.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class PemasukanFactoryTest {
    static String nama = "a";
    static String keterangan = "b";
    static long nominal = 1;
    static KategoriPemasukan kategoriPemasukan = mock(KategoriPemasukan.class);
    
    static LocalDateTime currentTime;
    static ILocalDateTimeFactory dateTimeFactory;
    
    @BeforeEach
    void setup(){
        currentTime = LocalDateTime.now();
        dateTimeFactory = mock(ILocalDateTimeFactory.class);
        when(dateTimeFactory.createLocalDateTime()).thenReturn(currentTime);
    }
    
    
    static IPemasukanBlueprint initializeBlueprintMock(){
        IPemasukanBlueprint blueprint = mock(IPemasukanBlueprint.class);
        when(blueprint.getNama()).thenReturn(nama);
        when(blueprint.getKeterangan()).thenReturn(keterangan);
        when(blueprint.getNominal()).thenReturn(nominal);
        when(blueprint.getKategoriPemasukan()).thenReturn(kategoriPemasukan);
        return blueprint;
    }
    
    @Test
    void create() {
        IPemasukanBlueprint blueprint = initializeBlueprintMock();
        IWorkspace workspace = mock(IWorkspace.class);
        
        PemasukanFactory factory = new PemasukanFactory();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Pemasukan pemasukan = factory.create(blueprint, workspace);
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(nominal, pemasukan.getNominal());
        assertSame(kategoriPemasukan, pemasukan.getKategori());
        assertSame(currentTime, pemasukan.getWaktu());
        
        verify(workspace, times(1)).addFinancialEvent(pemasukan);
    }
}