package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IFinancialEvents;
import xyz.rpletsgo.common.model.FinancialEvents;

import java.time.LocalDateTime;

@Entity
@Table
public class Pemasukan extends FinancialEvents {
    @Getter
    @Setter
    @ManyToOne
    KategoriPemasukan kategori;
}
