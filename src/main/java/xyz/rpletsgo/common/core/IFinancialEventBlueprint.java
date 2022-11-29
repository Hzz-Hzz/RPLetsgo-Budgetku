package xyz.rpletsgo.common.core;

import xyz.rpletsgo.workspace.core.IWorkspace;

public interface IFinancialEventBlueprint {
    String getId();
    String getNama();
    String getKeterangan();
    long getNominal();
    
    void setNama(String value);
    void setKeterangan(String value);
    void setNominal(long value);
    
    ILocalDateTimeFactory getLocalDateTimeFactory();
    void setLocalDateTimeFactory(ILocalDateTimeFactory localDateTimeFactory);
    IFinancialEvent create(IWorkspace workspace);
}
