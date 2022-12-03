package xyz.rpletsgo.pemasukan.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import xyz.rpletsgo.common.model.FinancialEvent;

@Entity
@Table
public class Pemasukan extends FinancialEvent {
    @Getter
    @Setter
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(cascade={CascadeType.REMOVE})
    KategoriPemasukan kategori;
}
