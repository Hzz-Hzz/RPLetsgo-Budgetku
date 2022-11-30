package xyz.rpletsgo.common.core;

import jakarta.persistence.*;
import xyz.rpletsgo.common.model.FinancialEventCreationSchedule;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
public class AutomaticFinancialEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    String id;
    
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    List<FinancialEventCreationSchedule> schedules = new ArrayList<>();
    
    public void triggerEventCreation(IWorkspace workspace){
        for (FinancialEventCreationSchedule schedule: schedules) {
            var createdFinancialEvents = schedule.triggerCreation();
            workspace.addFinancialEvents(createdFinancialEvents);
        }
    }
    
    public void addNewSchedule(FinancialEventCreationSchedule schedule){
        schedules.add(schedule);
    }
}
