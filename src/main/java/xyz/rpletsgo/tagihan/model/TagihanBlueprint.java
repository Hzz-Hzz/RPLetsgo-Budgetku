package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.rpletsgo.common.model.FinancialEventBlueprint;
import xyz.rpletsgo.tagihan.core.ITagihanBlueprint;

@Entity
@Table
public class TagihanBlueprint extends FinancialEventBlueprint implements ITagihanBlueprint {

}
