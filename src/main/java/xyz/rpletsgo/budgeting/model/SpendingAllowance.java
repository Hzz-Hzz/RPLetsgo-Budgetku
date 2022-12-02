package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class SpendingAllowance {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator="native")
    @Column(name = "id", updatable = false)
    private String id;
    
    @Getter
    @Setter
    @Column(name = "nama")
    String nama;
    
    @Getter
    @Column(name = "nominal")
    long nominal = 0;
    
    public SpendingAllowance(String nama, long nominal){
        this.nama = nama;
        this.nominal = nominal;
    }
    
    
    public void increaseNominal(long increment) {
        nominal += increment;
    }
}
