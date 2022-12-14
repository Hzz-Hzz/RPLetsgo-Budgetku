package xyz.rpletsgo.pemasukan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.pemasukan.core.IPemasukanFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PemasukanFactoryTest {
    
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(PemasukanFactory::new);
    }
    
    static String nama = "a";
    static String keterangan = "b";
    static long nominal = 1;
    static KategoriPemasukan kategoriPemasukan = mock(KategoriPemasukan.class);
    
    static LocalDateTime currentTime;
    static ILocalDateTimeFactory dateTimeFactory;
    
    @BeforeEach
    void setup(){
        currentTime = LocalDateTime.of(2000, 1, 1, 1, 1 );
        dateTimeFactory = mock(ILocalDateTimeFactory.class);
        when(dateTimeFactory.createLocalDateTime()).thenReturn(currentTime);
    }
    
    
    static IPemasukanFactory initializeFactory(){
        IPemasukanFactory factory = new PemasukanFactory();
        factory.setNama(nama);
        factory.setKeterangan(keterangan);
        factory.setNominal(nominal);
        factory.setKategoriPemasukan(kategoriPemasukan);
        return factory;
    }
    
    @Test
    void set(){
        var factory = new PemasukanFactory();
        factory.set(nama, keterangan, nominal, kategoriPemasukan);
        var pemasukan = factory.create(currentTime);
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(nominal, pemasukan.getNominal());
        assertSame(kategoriPemasukan, pemasukan.getKategori());
        assertSame(currentTime, pemasukan.getWaktu());
    }
    
    
    @Test
    void create() {
        IPemasukanFactory factory = initializeFactory();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Pemasukan pemasukan = factory.create();
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(nominal, pemasukan.getNominal());
        assertSame(kategoriPemasukan, pemasukan.getKategori());
        assertSame(currentTime, pemasukan.getWaktu());
    }
    
    
    @Test
    void createWithSpecificTime() {
        IPemasukanFactory factory = initializeFactory();
        LocalDateTime waktu = currentTime.plusSeconds(-1);
        Pemasukan pemasukan = factory.create(waktu);
        
        assertEquals(nama, pemasukan.getNama());
        assertEquals(keterangan, pemasukan.getKeterangan());
        assertEquals(nominal, pemasukan.getNominal());
        assertSame(kategoriPemasukan, pemasukan.getKategori());
        assertSame(waktu, pemasukan.getWaktu());
    }
}