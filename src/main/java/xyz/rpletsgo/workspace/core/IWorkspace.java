package xyz.rpletsgo.workspace.core;

import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.Collection;
import java.util.List;

public interface IWorkspace {
    String getId();
    String getNama();

    void setNama(String nama);
    List<KategoriPemasukan> getKategoriPemasukan();
    List<FinancialEvent> getFinancialEvents();
    List<SpendingAllowance> getSpendingAllowances();
    
    
    
    KategoriPemasukan getKategoriPemasukan(String id);
    SpendingAllowance getSpendingAllowanceOrThrow(String id);
    void addKategoriPemasukan(KategoriPemasukan kategoriPemasukan);
    void addSpendingAllowance(SpendingAllowance spendingAllowance);
    
    
    void removeSpendingAllowance(String spendingAllowanceId);

    void existFinancialEventOrThrow(String id);
    void addFinancialEvent(FinancialEvent financialEvent);
    void addFinancialEvents(Collection<FinancialEvent> financialEvent);
    
    
    void triggerAutomation();
}
