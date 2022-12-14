package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class KategoriPemasukan {
    @Getter
    @Setter
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
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(cascade={CascadeType.REMOVE})
    List<AlokasiSpendingAllowance> alokasiSpendingAllowances = new ArrayList<>();
    
    
    public List<SpendingAllowance> getSpendingAllowanceYangTerkait(){
        List<SpendingAllowance> ret = new ArrayList<>();
    
        for (AlokasiSpendingAllowance alokasiSpendingAllowance: alokasiSpendingAllowances) {
            ret.add(alokasiSpendingAllowance.getSpendingAllowance());
        }
        
        return ret;
    }
    
    public void addPemasukan(long nominal){
        for (AlokasiSpendingAllowance alokasiSpendingAllowance: alokasiSpendingAllowances) {
            alokasiSpendingAllowance.increaseNominal(nominal);
        }
    }
}
