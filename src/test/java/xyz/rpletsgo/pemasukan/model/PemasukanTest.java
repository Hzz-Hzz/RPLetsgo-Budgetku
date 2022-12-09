package xyz.rpletsgo.pemasukan.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

class PemasukanTest {
    
    @Test
    void valueUpdate() {
        String nama = "a";
        String keterangan = "b";
        var waktu = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        long nominal = 1;
        var kategori = mock(KategoriPemasukan.class);
        
        
        var pemasukan = new Pemasukan();
        pemasukan.valueUpdate(nama, keterangan, waktu, nominal, kategori);
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(waktu, pemasukan.getWaktu());
        assertEquals(nominal, pemasukan.getNominal());
        assertSame(kategori, pemasukan.getKategori());
    }
}