package xyz.rpletsgo.pengeluaran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.rpletsgo.pengeluaran.service.PengeluaranService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/pengeluaran")
public class PengeluaranController {
    @Autowired
    PengeluaranService pengeluaranService;

    @PostMapping("/create")
    @ResponseBody
    public String createPengeluaran(
            @RequestParam String workspaceId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam LocalDateTime waktu,
            @RequestParam long nominal,
            @RequestParam String spendingAllowanceId,
            @RequestParam String tagihanId
    ){
        pengeluaranService.create(workspaceId, nama, keterangan, waktu, nominal, spendingAllowanceId, tagihanId);
        return "success";
    }
}
