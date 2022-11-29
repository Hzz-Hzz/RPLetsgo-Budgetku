package xyz.rpletsgo.common.core;

import xyz.rpletsgo.workspace.core.IWorkspace;

public interface IFinancialEventFactory<B extends IFinancialEventBlueprint> {
    ILocalDateTimeFactory getLocalDateTimeFactory();
    void setLocalDateTimeFactory(ILocalDateTimeFactory localDateTimeFactory);
    IFinancialEvent create(B blueprint, IWorkspace workspace);
}
