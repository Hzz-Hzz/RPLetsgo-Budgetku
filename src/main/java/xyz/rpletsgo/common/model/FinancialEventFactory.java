package xyz.rpletsgo.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;
import xyz.rpletsgo.workspace.core.IWorkspace;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FinancialEventFactory implements IFinancialEventFactory {
    @Getter
    @Id
    @Column(updatable = false)
    String id;
    
    @Setter
    @Getter
    @Column
    String nama;
    
    @Setter
    @Getter
    @Column
    String keterangan;
    
    @Setter
    @Getter
    @Column
    long nominal;
    
    
    @Getter
    @Setter
    @Transient
    ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
    
    protected void sideEffect_initialize(
            IWorkspace workspace,
            FinancialEvent sideEffect_financialEvent)
    {
        sideEffect_financialEvent.setKeterangan(getKeterangan());
        sideEffect_financialEvent.setNama(getNama());
        sideEffect_financialEvent.setNominal(getNominal());
        sideEffect_financialEvent.setWaktu(getLocalDateTimeFactory().createLocalDateTime());
        
        workspace.addFinancialEvent(sideEffect_financialEvent);
    }
}
