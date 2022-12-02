package xyz.rpletsgo.common.core;

import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.model.FinancialEvent;

import java.time.LocalDateTime;

public interface IFinancialEventFactory {
    String getId();
    String getNama();
    String getKeterangan();
    long getNominal();
    
    void setNama(String value);
    void setKeterangan(String value);
    void setNominal(long value);
    
    ILocalDateTimeFactory getLocalDateTimeFactory();
    void setLocalDateTimeFactory(ILocalDateTimeFactory localDateTimeFactory);
    
    FinancialEvent create();
    FinancialEvent create(@Nullable LocalDateTime waktu);
}
