package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.common.model.FinancialEvent;

import java.time.LocalDateTime;

@Entity
@Table
public class Pemasukan extends FinancialEvent {
    @Getter
    @Setter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(cascade={CascadeType.REMOVE})
    KategoriPemasukan kategori;

    public Pemasukan() {
    }

    public Pemasukan(String nama, String keterangan, LocalDateTime waktu,
                     long nominal, KategoriPemasukan kategori) {
        super(null, nama, keterangan, waktu, nominal);
        this.kategori = kategori;
    }

    public void valueUpdate(String nama, String keterangan, LocalDateTime waktu,
                            long nominal, KategoriPemasukan kategori) {
        this.setNama(nama);
        this.setKeterangan(keterangan);
        this.setWaktu(waktu);
        this.setNominal(nominal);
        this.setKategori(kategori);
    }
}
