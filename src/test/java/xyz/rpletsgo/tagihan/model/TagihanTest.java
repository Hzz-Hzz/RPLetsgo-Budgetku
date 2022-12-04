package xyz.rpletsgo.tagihan.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagihanTest {
    
    @Test
    void updateValue() {
        String nama = "a";
        String keterangan = "b";
        var waktu = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        long nominal = 1;
        
        
        var tagihan = new Tagihan();
        tagihan.updateValue(nama, keterangan, waktu, nominal);
        
        assertEquals(nama, tagihan.getNama());
        assertEquals(keterangan, tagihan.getKeterangan());
        assertEquals(waktu, tagihan.getWaktu());
        assertEquals(nominal, tagihan.getNominal());
    }
}