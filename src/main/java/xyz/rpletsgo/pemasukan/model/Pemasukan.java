package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.model.FinancialEvent;

@Entity
@Table
public class Pemasukan extends FinancialEvent {
    @Getter
    @Setter
    @ManyToOne
    KategoriPemasukan kategori;
}
