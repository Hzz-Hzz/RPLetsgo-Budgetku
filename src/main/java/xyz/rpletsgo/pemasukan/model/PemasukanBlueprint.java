package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import xyz.rpletsgo.common.model.FinancialEventBlueprint;
import xyz.rpletsgo.pemasukan.core.IPemasukanBlueprint;

@Entity
@Table
public class PemasukanBlueprint extends FinancialEventBlueprint implements IPemasukanBlueprint {
    @Getter
    @ManyToOne
    KategoriPemasukan kategoriPemasukan;
}
