package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.rpletsgo.common.model.FinancialEvent;

import java.time.LocalDateTime;

@Entity
@Table
public class Tagihan extends FinancialEvent {
    public void updateValue(String nama, String keterangan, LocalDateTime waktu, long nominal) {
        setNama(nama);
        setKeterangan(keterangan);
        setNominal(nominal);
        setWaktu(waktu);
    }
    public void increaseNominal(long nominal) {
        this.nominal += nominal;
    }
}
