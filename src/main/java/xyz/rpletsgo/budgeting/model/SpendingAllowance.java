package xyz.rpletsgo.budgeting.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table
public class SpendingAllowance {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private String id;
    
    @Getter
    @Column(name = "nama")
    String nama;
    
    @Getter
    @Column(name = "nominal")
    long nominal = 0;
    
    
    public void increaseNominal(long increment) {
        nominal += increment;
    }
}