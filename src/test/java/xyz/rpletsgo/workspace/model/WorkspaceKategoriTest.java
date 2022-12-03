package xyz.rpletsgo.workspace.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanException;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkspaceKategoriTest {
    @Test
    void getKategoriPemasukan_shouldThrowIfNotFound(){
        var kategori1 = mock(KategoriPemasukan.class);
        when(kategori1.getId()).thenReturn("a");
        
        var workspace = new Workspace();
        workspace.addKategoriPemasukan(kategori1);
        
        assertThrows(KategoriPemasukanNotFoundException.class,
                     () -> workspace.getKategoriPemasukanOrThrow("b"));
    }
    
    @Test
    void getKategoriPemasukan_shouldReturnTheRequestedObject(){
        var kategori1 = mock(KategoriPemasukan.class);
        when(kategori1.getId()).thenReturn("a");
        
        var workspace = new Workspace();
        workspace.addKategoriPemasukan(kategori1);
        
        assertSame(kategori1,
                   workspace.getKategoriPemasukanOrThrow("a"));
    }
    
    
    
    
    @Test
    void removeKategoriPemasukan_ensureWorkspaceHaveOneKategori(){
        var kategori1 = mock(KategoriPemasukan.class);
        when(kategori1.getId()).thenReturn("a");
        
        var workspace = new Workspace();
        workspace.addKategoriPemasukan(kategori1);
        
        assertThrows(KategoriPemasukanException.class,
                     () -> workspace.removeKategoriPemasukan("a"));
    }
    @Test
    void removeKategoriPemasukan_removeCorrectly(){
        var kategori1 = mock(KategoriPemasukan.class);
        var kategori2 = mock(KategoriPemasukan.class);
        var kategori3 = mock(KategoriPemasukan.class);
        when(kategori1.getId()).thenReturn("a");
        when(kategori2.getId()).thenReturn("b");
        when(kategori3.getId()).thenReturn("c");
        
        var workspace = new Workspace();
        workspace.addKategoriPemasukan(kategori1);
        workspace.addKategoriPemasukan(kategori2);
        workspace.addKategoriPemasukan(kategori3);
        
        workspace.removeKategoriPemasukan("b");
        var remainingKategoris = workspace.getKategoriPemasukan();
        
        assertEquals(List.of(kategori1, kategori3),
                     remainingKategoris);
    }
    
    @Test
    void removeKategoriPemasukan_throwsIfNotFound(){
        var kategori1 = mock(KategoriPemasukan.class);
        var kategori2 = mock(KategoriPemasukan.class);
        when(kategori1.getId()).thenReturn("a");
        when(kategori2.getId()).thenReturn("b");
        
        var workspace = new Workspace();
        workspace.addKategoriPemasukan(kategori1);
        workspace.addKategoriPemasukan(kategori2);
        
        assertThrows(KategoriPemasukanNotFoundException.class,
                     () -> workspace.removeKategoriPemasukan("c"));
    }
}