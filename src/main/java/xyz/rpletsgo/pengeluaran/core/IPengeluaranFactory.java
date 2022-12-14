package xyz.rpletsgo.pengeluaran.core;

import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

public interface IPengeluaranFactory extends IFinancialEventFactory {
    SpendingAllowance getSumberDana();
    void setSumberDana(SpendingAllowance value);
    
    @Nullable
    Tagihan getTagihanYangDibayar();
    void setTagihanYangDibayar(@Nullable Tagihan value);
    
    
    @Override
    Pengeluaran create();
    
    @Override
    Pengeluaran create(@Nullable LocalDateTime waktu);
}
