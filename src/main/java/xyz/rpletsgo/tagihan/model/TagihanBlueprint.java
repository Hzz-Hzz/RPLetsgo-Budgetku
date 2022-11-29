package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.rpletsgo.common.model.FinancialEventBlueprint;
import xyz.rpletsgo.tagihan.core.ITagihanBlueprint;
import xyz.rpletsgo.workspace.core.IWorkspace;

@Entity
@Table
public class TagihanBlueprint extends FinancialEventBlueprint implements ITagihanBlueprint {
    @Override
    public Tagihan create(IWorkspace workspace) {
        Tagihan tagihan = new Tagihan();
        sideEffect_initialize(workspace, tagihan);
        return tagihan;
    }
}
