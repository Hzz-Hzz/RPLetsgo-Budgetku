package xyz.rpletsgo.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;

import java.time.LocalDateTime;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class FinancialEventFactory implements IFinancialEventFactory {
    @Getter
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
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
            FinancialEvent sideEffect_financialEvent,
            @Nullable LocalDateTime waktu
    ){
        sideEffect_financialEvent.setKeterangan(getKeterangan());
        sideEffect_financialEvent.setNama(getNama());
        sideEffect_financialEvent.setNominal(getNominal());
        
        if (waktu == null)
            sideEffect_financialEvent.setWaktu(getLocalDateTimeFactory().createLocalDateTime());
        else
            sideEffect_financialEvent.setWaktu(waktu);
    }
}