package xyz.rpletsgo.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;

import java.time.LocalDateTime;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
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
    
    protected FinancialEventFactory(String nama, String keterangan, long nominal){
        this.nama = nama;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }
    
    protected void set(String nama, String keterangan, long nominal){
        this.nama = nama;
        this.keterangan = keterangan;
        this.nominal = nominal;
    }
    
    
    @Getter
    @Setter
    @Transient
    ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
    
    protected void initializeFinancialEvent(
            FinancialEvent financialEvent,
            @Nullable LocalDateTime waktu
    ){
        financialEvent.setKeterangan(getKeterangan());
        financialEvent.setNama(getNama());
        financialEvent.setNominal(getNominal());
        
        if (waktu == null)
            financialEvent.setWaktu(getLocalDateTimeFactory().createLocalDateTime());
        else
            financialEvent.setWaktu(waktu);
    }
}
