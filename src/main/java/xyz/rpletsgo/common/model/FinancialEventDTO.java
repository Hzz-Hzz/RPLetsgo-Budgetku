package xyz.rpletsgo.common.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class FinancialEventDTO {
    String id;
    String nama;
    String keterangan;
    LocalDateTime waktu;
    long nominal;
    Map<String, String> data;
}
