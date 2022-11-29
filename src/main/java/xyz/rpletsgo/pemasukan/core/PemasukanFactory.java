package xyz.rpletsgo.pemasukan.core;

import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.workspace.core.IWorkspace;

public class PemasukanFactory
        extends FinancialEventFactory<IPemasukanBlueprint>
        implements IFinancialEventFactory<IPemasukanBlueprint> {
    @Getter
    @Setter
    ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
    
    @Override
    public Pemasukan create(IPemasukanBlueprint blueprint, IWorkspace workspace) {
        Pemasukan pemasukan = new Pemasukan();
        sideEffect_initialize(blueprint, workspace, pemasukan);
        pemasukan.setKategori(blueprint.getKategoriPemasukan());
        return pemasukan;
    }
}
