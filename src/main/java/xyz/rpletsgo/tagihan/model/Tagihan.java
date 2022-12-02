package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.rpletsgo.common.model.FinancialEvent;

@Entity
@Table
public class Tagihan extends FinancialEvent {
    public void increaseNominal(long nominal) {
        this.nominal -= nominal;
    }
}
