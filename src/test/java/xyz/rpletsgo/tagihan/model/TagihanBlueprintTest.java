package xyz.rpletsgo.tagihan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.tagihan.core.ITagihanFactory;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagihanFactoryTest {
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(TagihanFactory::new);
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
    
    static ITagihanFactory initializeBlueprint(){
        ITagihanFactory blueprint = new TagihanFactory();
        blueprint.setNama(nama);
        blueprint.setKeterangan(keterangan);
        blueprint.setNominal(nominal);
        return blueprint;
    }
    
    @Test
    void create() {
        IWorkspace workspace = mock(IWorkspace.class);
        
        ITagihanFactory factory = initializeBlueprint();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Tagihan tagihan = factory.create(workspace);
        
        assertEquals(nama, tagihan.getNama());
        assertEquals(keterangan, tagihan.getKeterangan());
        assertEquals(nominal, tagihan.getNominal());
        assertSame(currentTime, tagihan.getWaktu());
        
        verify(workspace, times(1)).addFinancialEvent(tagihan);
    }
}