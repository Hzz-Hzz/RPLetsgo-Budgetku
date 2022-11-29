package xyz.rpletsgo.tagihan.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class TagihanFactoryTest {
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
    
    static ITagihanBlueprint initializeBlueprintMock(){
        ITagihanBlueprint blueprint = mock(ITagihanBlueprint.class);
        when(blueprint.getNama()).thenReturn(nama);
        when(blueprint.getKeterangan()).thenReturn(keterangan);
        when(blueprint.getNominal()).thenReturn(nominal);
        return blueprint;
    }
    
    @Test
    void create() {
        ITagihanBlueprint blueprint = initializeBlueprintMock();
        IWorkspace workspace = mock(IWorkspace.class);
    
        TagihanFactory factory = new TagihanFactory();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Tagihan tagihan = factory.create(blueprint, workspace);
    
        assertEquals(nama, tagihan.getNama());
        assertEquals(keterangan, tagihan.getKeterangan());
        assertEquals(nominal, tagihan.getNominal());
        assertSame(currentTime, tagihan.getWaktu());
    
        verify(workspace, times(1)).addFinancialEvent(tagihan);
    }
}