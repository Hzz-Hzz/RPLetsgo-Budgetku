package xyz.rpletsgo.workspace.core;

import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;

import java.util.Collection;
import java.util.List;

public interface IWorkspace {
    String getId();
    String getNama();

    void setNama(String nama);
    List<KategoriPemasukan> getKategoriPemasukan();
    List<FinancialEvent> getFinancialEvents();
    List<SpendingAllowance> getSpendingAllowances();
    
    
    
    
    KategoriPemasukan getKategoriPemasukanOrThrow(String id);
    
    SpendingAllowance getSpendingAllowanceOrThrow(String id);
    
    List<SpendingAllowance> getSpendingAllowanceOrThrow(List<String> id);
    
    void addKategoriPemasukan(KategoriPemasukan kategoriPemasukan);
    void addSpendingAllowance(SpendingAllowance spendingAllowance);
    
    
    void removeSpendingAllowance(String spendingAllowanceId);
    
    void removeKategoriPemasukan(String kategoriPemasukanId);
    
    void existFinancialEventOrThrow(String id);

    List<FinancialEvent> getPemasukans();
    List<FinancialEvent> getPengeluarans();

    void addFinancialEvent(FinancialEvent financialEvent);
    void addFinancialEvents(Collection<FinancialEvent> financialEvent);
    List<FinancialEvent> getTagihan();
    
    void triggerAutomation();

    void deleteFinancialEventOrThrow(String financialEventId);
}
