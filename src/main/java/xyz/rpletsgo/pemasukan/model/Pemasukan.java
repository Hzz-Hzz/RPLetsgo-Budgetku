package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.model.FinancialEvent;

import java.time.LocalDateTime;

@Entity
@Table
public class Pemasukan extends FinancialEvent {
    @Getter
    @Setter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    KategoriPemasukan kategori;

    public Pemasukan(String nama, String keterangan, LocalDateTime waktu,
                     long nominal, KategoriPemasukan kategoriPemasukan) {

    }
}
