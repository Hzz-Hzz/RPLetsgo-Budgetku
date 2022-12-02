package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
public class Pengeluaran extends FinancialEvent {
    public Pengeluaran(String nama, String keterangan, LocalDateTime waktu, long nominal, SpendingAllowance sumberDana, @Nullable Tagihan tagihanYangDibayar) {
        super(null, nama, keterangan, waktu, nominal);
        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;
    }

    public Pengeluaran(SpendingAllowance sumberDana, @Nullable Tagihan tagihanYangDibayar) {
        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;
    }

    @Override
    public void setNominal(long nominal) {
        setSumberDanaTagihanNominal(this.sumberDana, this.tagihanYangDibayar, nominal);
    }

    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    SpendingAllowance sumberDana;

    @Nullable
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    Tagihan tagihanYangDibayar;

    public void setSumberDanaTagihanNominal(SpendingAllowance sumberDana, Tagihan tagihanYangDibayar, long nominal) {
        if(this.sumberDana != null) {
            this.sumberDana.increaseNominal(this.getNominal());
        }
        if(this.tagihanYangDibayar != null) {
            this.tagihanYangDibayar.increaseNominal(-this.getNominal());
        }

        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;

        this.nominal = nominal;
        if(this.sumberDana != null) {
            this.sumberDana.increaseNominal(-this.getNominal());
        }
        if(this.tagihanYangDibayar != null) {
            this.tagihanYangDibayar.increaseNominal(this.getNominal());
        }
    }

    public void valueUpdate(String nama,
                            String keteragan,
                            LocalDateTime waktu,
                            long nominal,
                            SpendingAllowance sumberDana,
                            Tagihan tagihanYangDibayar) {
        setNama(nama);
        setKeterangan(keteragan);
        setWaktu(waktu);
        setSumberDanaTagihanNominal(sumberDana, tagihanYangDibayar, nominal);
    }

}
