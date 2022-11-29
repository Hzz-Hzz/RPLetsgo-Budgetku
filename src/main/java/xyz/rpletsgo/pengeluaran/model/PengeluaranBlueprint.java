package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEventBlueprint;
import xyz.rpletsgo.pengeluaran.core.IPengeluaranBlueprint;
import xyz.rpletsgo.tagihan.model.Tagihan;

@Entity
@Table
public class PengeluaranBlueprint extends FinancialEventBlueprint implements IPengeluaranBlueprint {
    @Getter
    @ManyToOne
    SpendingAllowance sumberDana;
    
    @Getter
    @ManyToOne
    Tagihan tagihanYangDibayar;
}
