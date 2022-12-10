package xyz.rpletsgo.pemasukan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pemasukan.service.PemasukanService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(value={"/{workspaceId}/pemasukan", "/{workspaceId}/Pemasukan"})
public class PemasukanController {
    final String success = "success";
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    PemasukanService pemasukanService;

    @GetMapping("")
    @ResponseBody
    public List<FinancialEvent> getPemasukan (
            @PathVariable String workspaceId
    ){
        return pemasukanService.getPemasukansByWorkspace(workspaceId);
    }

    @PostMapping("/create")
    @ResponseBody
    public Pemasukan createPemasukan(
            @PathVariable String workspaceId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam String waktu,
            @RequestParam Long nominal,
            @RequestParam String kategoriPemasukanId
    ){
        var waktuLocalDateTime = LocalDate.parse(waktu, formatter).atStartOfDay();
        return pemasukanService.create(workspaceId, nama, keterangan, waktuLocalDateTime, nominal, kategoriPemasukanId);
    }

    @PostMapping("/update")
    @ResponseBody
    public Pemasukan updatePemasukan(
            @PathVariable String workspaceId,
            @RequestParam String pemasukanId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam String waktu,
            @RequestParam Long nominal,
            @RequestParam String kategoriPemasukanId
    ) {
        var waktuLocalDateTime = LocalDate.parse(waktu, formatter).atStartOfDay();
        return pemasukanService.update(workspaceId, pemasukanId, nama, keterangan, waktuLocalDateTime, nominal, kategoriPemasukanId);
    }

    @PostMapping("/delete/{pemasukanId}")
    @ResponseBody
    public String deletePemasukan(
            @PathVariable String workspaceId,
            @PathVariable String pemasukanId
    ) {
        pemasukanService.delete(workspaceId, pemasukanId);
        return success;
    }
}
