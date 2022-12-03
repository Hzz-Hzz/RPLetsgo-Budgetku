package xyz.rpletsgo.pemasukan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pemasukan.service.PemasukanService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/{workspaceId}/pemasukan")
public class PemasukanController {

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
            @RequestParam LocalDateTime waktu,
            @RequestParam Long nominal,
            @RequestParam String kategoriPemasukanId
    ){
        return pemasukanService.create(workspaceId, nama, keterangan, waktu, nominal, kategoriPemasukanId);
    }

    @PostMapping("/update")
    @ResponseBody
    public Pemasukan updatePemasukan(
            @PathVariable String workspaceId,
            @RequestParam String pemasukanId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam LocalDateTime waktu,
            @RequestParam Long nominal,
            @RequestParam String kategoriPemasukanId
    ) {
        return pemasukanService.update(workspaceId, pemasukanId, nama, keterangan, waktu, nominal, kategoriPemasukanId);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Pemasukan deletePemasukan(
            @PathVariable String workspaceId,
            @RequestParam String pemasukanId
    ) {
        return pemasukanService.delete(workspaceId, pemasukanId);
    }
}
