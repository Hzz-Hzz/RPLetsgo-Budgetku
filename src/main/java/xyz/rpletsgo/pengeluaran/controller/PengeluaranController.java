package xyz.rpletsgo.pengeluaran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pengeluaran.model.Pengeluaran;
import xyz.rpletsgo.pengeluaran.service.PengeluaranService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/{workspaceId}/pengeluaran")
public class PengeluaranController {
    String success = "success";

    @Autowired
    PengeluaranService pengeluaranService;

    @GetMapping("/")
    @ResponseBody
    public List<FinancialEvent> getPengeluarans(
            @PathVariable String workspaceId
    ){
        return pengeluaranService.getPengeluaransByWorkspace(workspaceId);
    }

    @PostMapping("/create")
    @ResponseBody
    public String createPengeluaran(
            @PathVariable String workspaceId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam LocalDateTime waktu,
            @RequestParam long nominal,
            @RequestParam String spendingAllowanceId,
            @RequestParam String tagihanId
    ){
        pengeluaranService.create(workspaceId, nama, keterangan,
                waktu, nominal, spendingAllowanceId, tagihanId);
        return success;
    }

    @PostMapping("/update/{pengeluaranId}")
    @ResponseBody
    public String updatePengeluaran(
            @PathVariable String workspaceId,
            @PathVariable String pengeluaranId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam LocalDateTime waktu,
            @RequestParam long nominal,
            @RequestParam String spendingAllowanceId,
            @RequestParam String tagihanId
    ){
        pengeluaranService.update(workspaceId, pengeluaranId, nama,
                keterangan, waktu, nominal,
                spendingAllowanceId, tagihanId);
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
