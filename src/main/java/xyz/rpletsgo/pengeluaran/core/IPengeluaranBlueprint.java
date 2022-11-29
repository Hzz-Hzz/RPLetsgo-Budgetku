package xyz.rpletsgo.pengeluaran.core;

import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.IFinancialEventBlueprint;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

public interface IPengeluaranBlueprint extends IFinancialEventBlueprint {
    SpendingAllowance getSumberDana();
    void setSumberDana(SpendingAllowance value);
    
    @Nullable
    Tagihan getTagihanYangDibayar();
    void setTagihanYangDibayar(@Nullable Tagihan value);
    
    @Override
    Pengeluaran create(IWorkspace workspace);
}
