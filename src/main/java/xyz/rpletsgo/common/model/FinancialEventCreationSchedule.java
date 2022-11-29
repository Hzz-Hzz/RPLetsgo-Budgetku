package xyz.rpletsgo.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
public class FinancialEventCreationSchedule {
    @Getter
    @Setter
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    @Getter
    @Setter
    @Column
    long timeIntervalAsSeconds;
    
    @Getter
    @Setter
    @Column
    LocalDateTime nextCreationDate;
    
    @Getter
    @Setter
    @OneToOne
    FinancialEventFactory financialEventFactory;
    
    @Getter
    @Setter
    @Transient
    ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
    
    void setToNextCreationDate(){
        nextCreationDate = nextCreationDate.plusSeconds(timeIntervalAsSeconds);
    }
    
    FinancialEventCreationSchedule(
        long timeIntervalAsSeconds,
        LocalDateTime nextCreationDate,
        FinancialEventFactory financialEventFactory
    ){
        this.timeIntervalAsSeconds = timeIntervalAsSeconds;
        this.nextCreationDate = nextCreationDate;
        this.financialEventFactory = financialEventFactory;
    }
    
    public List<FinancialEvent> triggerCreation(){
        List<FinancialEvent> ret = new ArrayList<>();
        LocalDateTime currentTime = localDateTimeFactory.createLocalDateTime();
        
        while (nextCreationDate.compareTo(currentTime) <= 0){
            FinancialEvent financialEvent = financialEventFactory.create(nextCreationDate);
            ret.add(financialEvent);
            
            setToNextCreationDate();
        }
        
        return ret;
    }
}
