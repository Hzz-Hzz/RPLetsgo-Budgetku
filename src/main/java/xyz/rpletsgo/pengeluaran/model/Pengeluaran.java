package xyz.rpletsgo.pengeluaran.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.model.Tagihan;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
public class Pengeluaran extends FinancialEvent {
    public Pengeluaran(String id, String nama, String keterangan, LocalDateTime waktu, long nominal, SpendingAllowance sumberDana, @Nullable Tagihan tagihanYangDibayar) {
        super(id, nama, keterangan, waktu, nominal);
        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;
    }

    public Pengeluaran(SpendingAllowance sumberDana, @Nullable Tagihan tagihanYangDibayar) {
        this.sumberDana = sumberDana;
        this.tagihanYangDibayar = tagihanYangDibayar;
    }

    @Setter
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    SpendingAllowance sumberDana;
    
    @Nullable
    @Setter
    @Getter
    @ManyToOne(cascade={CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    Tagihan tagihanYangDibayar;


}
