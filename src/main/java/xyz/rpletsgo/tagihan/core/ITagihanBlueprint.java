package xyz.rpletsgo.tagihan.core;

import xyz.rpletsgo.common.core.IFinancialEventBlueprint;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

public interface ITagihanBlueprint extends IFinancialEventBlueprint {
    @Override
    Tagihan create(IWorkspace workspace);
}
