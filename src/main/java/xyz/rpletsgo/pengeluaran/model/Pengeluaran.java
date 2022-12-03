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
    public Pengeluaran(String nama, String keterangan, LocalDateTime waktu) {
        super(null, nama, keterangan, waktu, 0);
    }

    @Override
    public void setNominal(long nominal) {
        setSumberDanaTagihanNominal(this.sumberDana, this.tagihanYangDibayar, nominal);
    }

    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(cascade={CascadeType.REMOVE})
    SpendingAllowance sumberDana;

    @Nullable
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(cascade={CascadeType.REMOVE})
    Tagihan tagihanYangDibayar;

    public void setSumberDanaTagihanNominal(SpendingAllowance sumberDana, Tagihan tagihanYangDibayar, long nominal) {
        if(this.sumberDana != null) {
            this.sumberDana.increaseNominal(this.nominal);
        }
        if(this.tagihanYangDibayar != null) {
            this.tagihanYangDibayar.increaseNominal(this.nominal);
        }

        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;

        this.nominal = nominal;
        if(this.sumberDana != null) {
            this.sumberDana.increaseNominal(-this.nominal);
        }
        if(this.tagihanYangDibayar != null) {
            this.tagihanYangDibayar.increaseNominal(-this.nominal);
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
