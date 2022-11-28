package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.*;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvents;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.util.Optional;

@Entity
@Table
public class Pengeluaran extends FinancialEvents {
    @ManyToOne
    SpendingAllowance sumberDana;
    
    @ManyToOne
    Tagihan tagihanYangDibayar;
}
