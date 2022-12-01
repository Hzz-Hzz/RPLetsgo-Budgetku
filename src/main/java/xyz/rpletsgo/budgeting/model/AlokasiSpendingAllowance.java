package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table
public class AlokasiSpendingAllowance {
    @Getter
    @Setter
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Getter
    @Setter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST})
    SpendingAllowance spendingAllowance;
    
    @Getter
    @Setter
    @Column(name="besarAlokasi")
    double besarAlokasi;
    
    
    public long increaseNominal(long nominalKeseluruhan) {
        Double incrementAsDouble = nominalKeseluruhan * besarAlokasi;
        long increment = incrementAsDouble.longValue();
        
        spendingAllowance.increaseNominal(increment);
        return increment;
    }
}
