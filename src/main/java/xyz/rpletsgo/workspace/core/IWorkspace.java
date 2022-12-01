package xyz.rpletsgo.workspace.core;

import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;

import java.util.Collection;
import java.util.List;

public interface IWorkspace {
    String getId();
    String getNama();
    List<KategoriPemasukan> getKategoriPemasukan();
    List<FinancialEvent> getFinancialEvents();
    List<SpendingAllowance> getSpendingAllowances();
    void addFinancialEvent(FinancialEvent financialEvent);
    void addFinancialEvents(Collection<FinancialEvent> financialEvent);
    
    void triggerAutomation();
}
