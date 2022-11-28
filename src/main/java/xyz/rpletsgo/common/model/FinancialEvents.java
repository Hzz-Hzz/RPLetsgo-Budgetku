package xyz.rpletsgo.common.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEvents;

import java.time.LocalDateTime;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FinancialEvents implements IFinancialEvents {
    @Getter
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Getter
    @Setter
    @Column
    String nama;
    
    @Getter
    @Setter
    @Column
    String keterangan;
    
    @Getter
    @Setter
    @Column
    LocalDateTime waktu;
    
    @Getter
    @Setter
    @Column
    long nominal;
}
