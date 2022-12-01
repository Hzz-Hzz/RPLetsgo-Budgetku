package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class AlokasiSpendingAllowance {
    @Getter
    @Setter
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Getter
    @Setter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
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
