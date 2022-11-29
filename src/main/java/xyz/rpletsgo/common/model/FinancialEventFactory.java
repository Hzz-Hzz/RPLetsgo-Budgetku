package xyz.rpletsgo.common.model;

import xyz.rpletsgo.common.core.IFinancialEventBlueprint;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.workspace.core.IWorkspace;

public abstract class FinancialEventFactory<B extends IFinancialEventBlueprint>
        implements IFinancialEventFactory<B> {
    
    
    protected void sideEffect_initialize(
            IFinancialEventBlueprint blueprint,
            IWorkspace workspace,
            FinancialEvent sideEffect_financialEvent)
    {
        sideEffect_financialEvent.setKeterangan(blueprint.getKeterangan());
        sideEffect_financialEvent.setNama(blueprint.getNama());
        sideEffect_financialEvent.setNominal(blueprint.getNominal());
        sideEffect_financialEvent.setWaktu(getLocalDateTimeFactory().createLocalDateTime());
        
        workspace.addFinancialEvent(sideEffect_financialEvent);
    }
}
