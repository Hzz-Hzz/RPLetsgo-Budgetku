package xyz.rpletsgo.workspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.AutomaticFinancialEvent;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.ArrayList;
import java.util.Collection;
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
    @OneToMany(cascade={CascadeType.REMOVE, CascadeType.PERSIST})
    List<KategoriPemasukan> kategoriPemasukan = new ArrayList<>();
    
    @Getter
    @OneToMany(cascade={CascadeType.REMOVE, CascadeType.PERSIST})
    List<FinancialEvent> financialEvents = new ArrayList<>();
    
    @Getter
    @OneToMany(cascade={CascadeType.REMOVE, CascadeType.PERSIST})
    List<SpendingAllowance> spendingAllowances = new ArrayList<>();
    
    @OneToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST})
    AutomaticFinancialEvent automaticFinancialEvent;
    
    
    @Override
    public void addFinancialEvent(FinancialEvent financialEvent) {
        financialEvents.add(financialEvent);
    }
    
    @Override
    public void addFinancialEvents(Collection<FinancialEvent> financialEvents) {
        this.financialEvents.addAll(financialEvents);
    }
    
    // TODO automatic financial events
}
