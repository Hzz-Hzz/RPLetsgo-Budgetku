package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
public class Pengeluaran extends FinancialEvent {
    public Pengeluaran(String nama, String keterangan, LocalDateTime waktu, SpendingAllowance sumberDana, Tagihan tagihanYangDibayar) {
        super(null, nama, keterangan, waktu, 0);
        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;
    }

    @Setter
    @Getter
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne()
    
    SpendingAllowance sumberDana;

    @Nullable
    @Getter
    @Setter
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne()
    Tagihan tagihanYangDibayar;

    @Override
    public void setNominal(long nominal) {
        long nominalDiff = this.nominal - nominal;

        this.sumberDana.increaseNominal(nominalDiff);
        if(this.tagihanYangDibayar != null) {
            this.tagihanYangDibayar.increaseNominal(nominalDiff);
        }

        this.nominal = nominal;
    }

    public void valueUpdate(String nama,
                            String keteragan,
                            LocalDateTime waktu,
                            long nominal) {
        setNama(nama);
        setKeterangan(keteragan);
        setWaktu(waktu);
        setNominal(nominal);
    }

}
