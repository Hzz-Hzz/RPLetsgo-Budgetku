package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;

@Entity
@Table
public class Pengeluaran extends FinancialEvent {
    @Setter
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    SpendingAllowance sumberDana;
    
    @Nullable
    @Setter
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    Tagihan tagihanYangDibayar;
}
