package xyz.rpletsgo.common.core;

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
    IFinancialEvent create();
}
