package xyz.rpletsgo.pengeluaran.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PengeluaranFactoryTest {
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(PengeluaranFactory::new);
    }
    
    static String nama = "a";
    static String keterangan = "b";
    static long nominal = 1;
    static SpendingAllowance sumberDana = mock(SpendingAllowance.class);
    
    static LocalDateTime currentTime;
    static ILocalDateTimeFactory dateTimeFactory;
    
    @BeforeEach
    void setup(){
        currentTime = LocalDateTime.of(2000, 1, 1, 1, 1);
        dateTimeFactory = mock(ILocalDateTimeFactory.class);
        when(dateTimeFactory.createLocalDateTime()).thenReturn(currentTime);
    }
    
    static IPengeluaranFactory initializeFactory(Tagihan tagihan){
        IPengeluaranFactory factory = new PengeluaranFactory();
        factory.setNama(nama);
        factory.setKeterangan(keterangan);
        factory.setNominal(nominal);
        factory.setSumberDana(sumberDana);
        factory.setTagihanYangDibayar(tagihan);
        return factory;
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
    
    @Test
    void createWithSpecificTime(){
        IPengeluaranFactory factory = initializeFactory(null);
        var waktu = currentTime.plusSeconds(-1);
        Pengeluaran pengeluaran = factory.create(waktu);
    
        assertEquals(nama, pengeluaran.getNama());
        assertEquals(keterangan, pengeluaran.getKeterangan());
        assertEquals(nominal, pengeluaran.getNominal());
        assertSame(sumberDana, pengeluaran.getSumberDana());
        assertSame(null, pengeluaran.getTagihanYangDibayar());
        assertSame(waktu, pengeluaran.getWaktu());
    }
    
    static void verifyCreateWorkingCorrectly(Tagihan tagihan){
        IPengeluaranFactory factory = initializeFactory(tagihan);
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Pengeluaran pengeluaran = factory.create();
        
        assertEquals(nama, pengeluaran.getNama());
        assertEquals(keterangan, pengeluaran.getKeterangan());
        assertEquals(nominal, pengeluaran.getNominal());
        assertSame(sumberDana, pengeluaran.getSumberDana());
        assertSame(tagihan, pengeluaran.getTagihanYangDibayar());
        assertSame(currentTime, pengeluaran.getWaktu());
    }
}