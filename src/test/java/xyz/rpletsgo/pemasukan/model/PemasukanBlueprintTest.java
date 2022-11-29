package xyz.rpletsgo.pemasukan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.pemasukan.core.IPemasukanBlueprint;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PemasukanBlueprintTest {
    
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(PemasukanBlueprint::new);
    }
    
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
    
    
    static IPemasukanBlueprint initializeBlueprint(){
        IPemasukanBlueprint blueprint = new PemasukanBlueprint();
        blueprint.setNama(nama);
        blueprint.setKeterangan(keterangan);
        blueprint.setNominal(nominal);
        blueprint.setKategoriPemasukan(kategoriPemasukan);
        return blueprint;
    }
    
    
    @Test
    void create() {
        IWorkspace workspace = mock(IWorkspace.class);
        
        IPemasukanBlueprint factory = initializeBlueprint();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Pemasukan pemasukan = factory.create(workspace);
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(nominal, pemasukan.getNominal());
        assertSame(kategoriPemasukan, pemasukan.getKategori());
        assertSame(currentTime, pemasukan.getWaktu());
        
        verify(workspace, times(1)).addFinancialEvent(pemasukan);
    }
}