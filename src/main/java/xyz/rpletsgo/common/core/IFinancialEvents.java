package xyz.rpletsgo.common.core;

import java.time.LocalDateTime;

public interface IFinancialEvents {
    String getId();
    String getNama();
    String getKeterangan();
    LocalDateTime getWaktu();
    long getNominal();
    
    void setNama(String value);
    void setKeterangan(String value);
    void setWaktu(LocalDateTime value);
    void setNominal(long value);
}
