package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.http.HttpStatus;
import xyz.rpletsgo.common.exceptions.GeneralException;


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
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(cascade={CascadeType.REMOVE})
    SpendingAllowance spendingAllowance;
    
    @Getter
    @Column(name="besarAlokasi")
    double besarAlokasi;
    
    public void setBesarAlokasi(double value){
        if (value < 0 || value > 1){
            throw new GeneralException("Besar alokasi spending allowance harus antara 0 dan 1", HttpStatus.BAD_REQUEST);
        }
        this.besarAlokasi = value;
    }
    
    
    public long increaseNominal(long nominalKeseluruhan) {
        Double incrementAsDouble = nominalKeseluruhan * besarAlokasi;
        long increment = incrementAsDouble.longValue();
        
        spendingAllowance.increaseNominal(increment);
        return increment;
    }
}
