package xyz.rpletsgo.tagihan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.rpletsgo.common.core.IFinancialEvents;
import xyz.rpletsgo.common.model.FinancialEvents;

@Entity
@Table
public class Tagihan extends FinancialEvents {
}
