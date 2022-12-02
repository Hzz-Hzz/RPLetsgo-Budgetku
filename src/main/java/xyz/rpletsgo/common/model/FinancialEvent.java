package xyz.rpletsgo.common.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEvent;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FinancialEvent implements IFinancialEvent {
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
