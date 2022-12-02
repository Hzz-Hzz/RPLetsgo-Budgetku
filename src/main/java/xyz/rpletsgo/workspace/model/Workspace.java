package xyz.rpletsgo.workspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    String id;
    
    @Getter
    @Column
    String nama;
    
    @Getter
    @OneToMany(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    List<KategoriPemasukan> kategoriPemasukan = new ArrayList<>();
    
    @Getter
    @OneToMany(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    List<FinancialEvent> financialEvents = new ArrayList<>();
    
    @Getter
    @OneToMany(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    List<SpendingAllowance> spendingAllowances = new ArrayList<>();
    
    @Getter
    @Setter
    @OneToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    AutomaticFinancialEvent automaticFinancialEvent;
    
    
    @Override
    public KategoriPemasukan getKategoriPemasukan(String id) {
        for (var kategori: kategoriPemasukan) {
            if (kategori.getId().equals(id))
                return kategori;
        }
        throw new KategoriPemasukanNotFoundException("kategori pemasukan not found");
    }
    
    @Override
    public SpendingAllowance getSpendingAllowanceOrThrow(String id) {
        for (var spendingAllowance: spendingAllowances) {
            if (spendingAllowance.getId().equals(id))
                return spendingAllowance;
        }
        throw new SpendingAllowanceNotFoundException("kategori pemasukan not found");
    }
    
    @Override
    public void addKategoriPemasukan(KategoriPemasukan kategoriPemasukan) {
        this.kategoriPemasukan.add(kategoriPemasukan);
    }
    
    @Override
    public void addSpendingAllowance(SpendingAllowance spendingAllowance) {
        spendingAllowances.add(spendingAllowance);
    }
    
    @Override
    public void addFinancialEvent(FinancialEvent financialEvent) {
        financialEvents.add(financialEvent);
    }
    
    @Override
    public void addFinancialEvents(Collection<FinancialEvent> financialEvents) {
        this.financialEvents.addAll(financialEvents);
    }
    
    
    
    @Override
    public void triggerAutomation(){
        automaticFinancialEvent.triggerEventCreation(this);
    }
}