package xyz.rpletsgo.workspace.core;

import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.Collection;
import java.util.List;

public interface IWorkspace {
    String getId();
    String getNama();
    List<KategoriPemasukan> getKategoriPemasukan();
    List<FinancialEvent> getFinancialEvents();
    List<SpendingAllowance> getSpendingAllowances();
    
    
    
    KategoriPemasukan getKategoriPemasukan(String id);
    SpendingAllowance getSpendingAllowanceOrThrow(String id);
    void addKategoriPemasukan(KategoriPemasukan kategoriPemasukan);
    void addSpendingAllowance(SpendingAllowance spendingAllowance);
    
    
    void addFinancialEvent(FinancialEvent financialEvent);
    void addFinancialEvents(Collection<FinancialEvent> financialEvent);
    
    
    void triggerAutomation();
}
