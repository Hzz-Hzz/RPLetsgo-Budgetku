package xyz.rpletsgo.tagihan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.tagihan.core.ITagihanFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    static LocalDateTime currentTime2;
    static ILocalDateTimeFactory dateTimeFactory;
    
    @BeforeEach
    void setup(){
        currentTime = LocalDateTime.now();
        currentTime2 = currentTime.plusSeconds(1);
        dateTimeFactory = mock(ILocalDateTimeFactory.class);
        when(dateTimeFactory.createLocalDateTime()).thenReturn(currentTime);
    }
    
    static ITagihanFactory initializeFactory(){
        ITagihanFactory factory = new TagihanFactory();
        factory.setNama(nama);
        factory.setKeterangan(keterangan);
        factory.setNominal(nominal);
        return factory;
    }
    
    @Test
    void create() {
        ITagihanFactory factory = initializeFactory();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Tagihan tagihan = factory.create();
        
        assertEquals(nama, tagihan.getNama());
        assertEquals(keterangan, tagihan.getKeterangan());
        assertEquals(nominal, tagihan.getNominal());
        assertSame(currentTime, tagihan.getWaktu());
    }
    
    
    @Test
    void createWithSpecificTime() {
        ITagihanFactory factory = initializeFactory();
        factory.setLocalDateTimeFactory(dateTimeFactory);
        Tagihan tagihan = factory.create(currentTime2);
        
        assertEquals(nama, tagihan.getNama());
        assertEquals(keterangan, tagihan.getKeterangan());
        assertEquals(nominal, tagihan.getNominal());
        assertSame(currentTime2, tagihan.getWaktu());
    }
}