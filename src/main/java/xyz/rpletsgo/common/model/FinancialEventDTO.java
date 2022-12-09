package xyz.rpletsgo.common.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class FinancialEventDTO {
    String id;
    String type;
    String nama;
    String keterangan;
    String waktu;
    long nominal;
    String kategoriId; // buat pemasukan
}
