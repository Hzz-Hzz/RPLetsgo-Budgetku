package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(cascade={CascadeType.REMOVE})
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
