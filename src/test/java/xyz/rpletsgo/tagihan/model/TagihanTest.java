package xyz.rpletsgo.tagihan.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.tagihan.core.ITagihanBlueprint;
import xyz.rpletsgo.tagihan.core.TagihanFactory;
import xyz.rpletsgo.workspace.core.IWorkspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TagihanTest {
    static String nama = "a";
    static String keterangan = "b";
    static long nominal = 1;
    static KategoriPemasukan kategoriPemasukan = mock(KategoriPemasukan.class);
    
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
        Tagihan pemasukan = factory.create(blueprint, workspace);
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(nominal, pemasukan.getNominal());
        
        verify(workspace, times(1)).addFinancialEvent(pemasukan);
    }
}

