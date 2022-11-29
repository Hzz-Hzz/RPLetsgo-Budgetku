package xyz.rpletsgo.pengeluaran.core;

import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEventFactory;
import xyz.rpletsgo.common.core.ILocalDateTimeFactory;
import xyz.rpletsgo.common.core.LocalDateTimeFactory;
import xyz.rpletsgo.common.model.FinancialEventFactory;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.workspace.core.IWorkspace;

public class PengeluaranFactory extends FinancialEventFactory<IPengeluaranBlueprint>
        implements IFinancialEventFactory<IPengeluaranBlueprint> {
    @Getter
    @Setter
    ILocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
    
    @Override
    public Pengeluaran create(IPengeluaranBlueprint blueprint, IWorkspace workspace) {
        Pengeluaran pengeluaran = new Pengeluaran();
        sideEffect_initialize(blueprint, workspace, pengeluaran);
        
        pengeluaran.setSumberDana(blueprint.getSumberDana());
        pengeluaran.setTagihanYangDibayar(blueprint.getTagihanYangDibayar());
        return pengeluaran;
    }
}
