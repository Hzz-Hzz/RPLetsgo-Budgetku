package xyz.rpletsgo.pengeluaran.core;

import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.IFinancialEventBlueprint;
import xyz.rpletsgo.tagihan.model.Tagihan;

public interface IPengeluaranBlueprint extends IFinancialEventBlueprint {
    SpendingAllowance getSumberDana();
    
    @Nullable
    Tagihan getTagihanYangDibayar();
}
