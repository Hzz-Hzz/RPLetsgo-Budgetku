package xyz.rpletsgo.workspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import xyz.rpletsgo.budgeting.core.AlokasiSpendingAllowanceFactory;
import xyz.rpletsgo.budgeting.core.KategoriPemasukanFactory;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanException;
import xyz.rpletsgo.budgeting.exceptions.KategoriPemasukanNotFoundException;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceException;
import xyz.rpletsgo.budgeting.exceptions.SpendingAllowanceNotFoundException;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.AutomaticFinancialEvent;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;
import xyz.rpletsgo.pengeluaran.exceptions.FinancialEventNotFoundException;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(
        cascade={CascadeType.REMOVE},
        fetch = FetchType.EAGER
    )
    List<KategoriPemasukan> kategoriPemasukan = new ArrayList<>();
    
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(
        cascade={CascadeType.REMOVE},
        fetch = FetchType.EAGER
    )
    List<FinancialEvent> financialEvents = new ArrayList<>();
    
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(
        cascade={CascadeType.REMOVE},
        fetch = FetchType.EAGER
    )
    List<SpendingAllowance> spendingAllowances = new ArrayList<>();
    
    @Getter
    @Setter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToOne(
        cascade={CascadeType.REMOVE},
        fetch = FetchType.EAGER
    )
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
        throw new SpendingAllowanceNotFoundException("Spending allowance not found");
    }
    @Override
    public List<SpendingAllowance> getSpendingAllowanceOrThrow(List<String> id) {
        var ret = new ArrayList<SpendingAllowance>();
        
        for (String spendingAllowanceId: id) {
            ret.add(getSpendingAllowanceOrThrow(spendingAllowanceId));
        }
        return ret;
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
    public void removeSpendingAllowance(String spendingAllowanceId) {
        if (spendingAllowances.size() <= 1)
            throw new SpendingAllowanceException("Workspace must have at least one spending allowance");
        
        for (SpendingAllowance spendingAllowance: spendingAllowances) {
            if (spendingAllowance.getId().equals(spendingAllowanceId)) {
                spendingAllowances.remove(spendingAllowance);
                return;
            }
        }
        
        throw new SpendingAllowanceNotFoundException("Spending allowance not found");
    }
    
    
    @Override
    public void removeKategoriPemasukan(String kategoriPemasukanId) {
        if (kategoriPemasukan.size() <= 1)
            throw new KategoriPemasukanException("Workspace must have at least one spending allowance");
        
        for (var kategori: kategoriPemasukan) {
            var areIdEqual = Objects.equals(kategori.getId(), kategoriPemasukanId);
            if (areIdEqual) {
                kategoriPemasukan.remove(kategori);
                return;
            }
        }
        
        throw new KategoriPemasukanNotFoundException("Kategori pemasukan not found");
    }

    @Override
    public void existFinancialEventOrThrow(String id) {
        for (var financialEvent: financialEvents) {
            if (financialEvent.getId().equals(id))
                return;
        }
        throw new FinancialEventNotFoundException("Financial Event not found");
    }

    @Override
    public List<FinancialEvent> getPengeluarans() {
        return financialEvents.stream()
                .filter(Pengeluaran.class::isInstance)
                .collect(Collectors.toList());
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
    public List<FinancialEvent> getTagihan() {
        return financialEvents.stream()
                .filter(Tagihan.class::isInstance)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFinancialEventOrThrow(String financialEventId) {
        this.existFinancialEventOrThrow(financialEventId);
        financialEvents.removeIf(financialEvent -> (Objects.equals(financialEvent.getId(), financialEventId)));
    }

    @Override
    public void triggerAutomation(){
        automaticFinancialEvent.triggerEventCreation(this);
    }

    @Override
    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String getNama() {
        return nama;
    }

    @Override
    public String getId() {
        return id;
    }

    
    @Transient
    KategoriPemasukanFactory kategoriPemasukanFactory = new KategoriPemasukanFactory();
    @Transient
    AlokasiSpendingAllowanceFactory alokasiSpendingAllowanceFactory = new AlokasiSpendingAllowanceFactory();
    
    public void initialize(){
        var spendingAllowance = new SpendingAllowance(null, "default", 0);
        var kategori = kategoriPemasukanFactory.create(
            "default"
        );
        var alokasi = alokasiSpendingAllowanceFactory.create(
            List.of(spendingAllowance), List.of(100.0)
        );
        kategori.setAlokasiSpendingAllowances(alokasi);
        
        automaticFinancialEvent = new AutomaticFinancialEvent();
        
        addSpendingAllowance(spendingAllowance);
        addKategoriPemasukan(kategori);
    }
}
