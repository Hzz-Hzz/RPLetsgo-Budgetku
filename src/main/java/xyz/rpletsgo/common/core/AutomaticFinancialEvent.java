package xyz.rpletsgo.common.core;

import jakarta.persistence.*;
import xyz.rpletsgo.common.model.FinancialEventCreationSchedule;

import java.util.List;

@Table
@Entity
public class AutomaticFinancialEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    String id;
    
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<FinancialEventCreationSchedule> schedules;
}
