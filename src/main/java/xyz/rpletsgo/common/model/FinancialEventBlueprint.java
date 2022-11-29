package xyz.rpletsgo.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import xyz.rpletsgo.common.core.IFinancialEventBlueprint;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FinancialEventBlueprint implements IFinancialEventBlueprint {
    @Getter
    @Id
    @Column(updatable = false)
    String id;
    
    @Getter
    @Column
    String nama;
    
    @Getter
    @Column
    String keterangan;
    
    @Getter
    @Column
    long nominal;
}
