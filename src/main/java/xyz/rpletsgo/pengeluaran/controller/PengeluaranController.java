package xyz.rpletsgo.pengeluaran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.pengeluaran.service.PengeluaranService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/{workspaceId}/pengeluaran")
public class PengeluaranController {
    String success = "success";
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    PengeluaranService pengeluaranService;

    @GetMapping("/")
    @ResponseBody
    public List<FinancialEvent> getPengeluarans(
            @PathVariable String workspaceId
    ){
        return pengeluaranService.getPengeluaransByWorkspace(workspaceId);
    }

    @GetMapping("/{pengeluaranId}")
    @ResponseBody
    public FinancialEvent getPengeluaranById(
            @PathVariable String workspaceId,
            @PathVariable String  pengeluaranId
    ){
        return pengeluaranService.getPengeluaranById(workspaceId, pengeluaranId);
    }

    @PostMapping("/create")
    @ResponseBody
    public String createPengeluaran(
            @PathVariable String workspaceId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam String waktu,
            @RequestParam long nominal,
            @RequestParam String spendingAllowanceId,
            @RequestParam Optional<String> tagihanId
    ){
        LocalDateTime dateTime = LocalDate.parse(waktu, formatter).atStartOfDay();
        pengeluaranService.create(workspaceId, nama, keterangan,
                dateTime, nominal, spendingAllowanceId, tagihanId.orElse(null));
        return success;
    }

    @PostMapping("/update/{pengeluaranId}")
    @ResponseBody
    public String updatePengeluaran(
            @PathVariable String workspaceId,
            @PathVariable String pengeluaranId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam String waktu,
            @RequestParam long nominal,
            @RequestParam String spendingAllowanceId,
            @RequestParam Optional<String> tagihanId
    ){
        LocalDateTime dateTime = LocalDate.parse(waktu, formatter).atStartOfDay();
        pengeluaranService.update(workspaceId, pengeluaranId, nama,
                keterangan, dateTime, nominal);
        return success;
    }

    @DeleteMapping("/delete/{pengeluaranId}")
    @ResponseBody
    public String deletePengeluaran(
            @PathVariable String workspaceId,
            @PathVariable String pengeluaranId
    ){
        pengeluaranService.delete(workspaceId, pengeluaranId);
        return success;
    }

}
