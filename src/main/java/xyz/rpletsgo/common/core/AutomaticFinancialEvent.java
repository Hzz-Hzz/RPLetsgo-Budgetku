package xyz.rpletsgo.common.core;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
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
    
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(
        fetch = FetchType.EAGER
    )
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
