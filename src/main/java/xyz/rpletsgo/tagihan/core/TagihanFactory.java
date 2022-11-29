package xyz.rpletsgo.tagihan.core;

import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.tagihan.model.Tagihan;
import xyz.rpletsgo.workspace.core.IWorkspace;

public class TagihanFactory
        extends FinancialEventFactory<ITagihanBlueprint>
        implements IFinancialEventFactory<ITagihanBlueprint> {
    @Getter
    @Setter
    ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
    
    @Override
    public Tagihan create(ITagihanBlueprint blueprint, IWorkspace workspace) {
        Tagihan tagihan = new Tagihan();
        sideEffect_initialize(blueprint, workspace, tagihan);
        return tagihan;
    }
}
