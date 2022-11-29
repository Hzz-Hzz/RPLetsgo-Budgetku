package xyz.rpletsgo.workspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.List;

@Entity
@Table
public class Workspace implements IWorkspace {
    @Getter
    @Id
    @Column(updatable = false)
    String id;
    
    @Getter
    @Column
    String nama;
    
    @Getter
    @OneToMany
    List<KategoriPemasukan> kategoriPemasukan;
    
    @Getter
    @OneToMany
    List<FinancialEvent> financialEvents;
    
    @Getter
    @OneToMany
    List<SpendingAllowance> spendingAllowances;
    
    @Override
    public void addFinancialEvent(FinancialEvent financialEvent) {
        financialEvents.add(financialEvent);
    }
    
    // TODO automatic financial events
}
