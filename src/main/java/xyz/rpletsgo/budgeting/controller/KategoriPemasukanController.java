package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.budgeting.model.KategoriPemasukan;
import xyz.rpletsgo.budgeting.service.KategoriPemasukanService;

import java.util.List;

@Controller
@RequestMapping("/{workspaceId}/kategori-pemasukan")
public class KategoriPemasukanController {
    String success = "success";
    
    @Autowired
    KategoriPemasukanService kategoriPemasukanService;
    
    
    @PostMapping("/create")
    @ResponseBody
    public KategoriPemasukan createKategoriPemasukan(
        @PathVariable String workspaceId,
        @RequestParam String nama,
        @RequestParam(value = "besarAlokasi[]") Double[] besarAlokasi,
        @RequestParam(value = "spendingAllowanceId[]") String[] spendingAllowanceId
    ){
        return kategoriPemasukanService.create(workspaceId, nama, besarAlokasi, spendingAllowanceId);
    }

    @PostMapping("/update")
    @ResponseBody
    public KategoriPemasukan updateKategoriPemasukan(
        @PathVariable String workspaceId,
         @RequestParam String kategoriPemasukanId,
         @RequestParam String nama,
         @RequestParam(value = "besarAlokasi[]") Double[] besarAlokasi,
         @RequestParam(value = "spendingAllowanceId[]") String[] spendingAllowanceId
    ){
        return kategoriPemasukanService.update(
            workspaceId, kategoriPemasukanId, nama,
            besarAlokasi, spendingAllowanceId);
    }

    
    @GetMapping("")
    @ResponseBody
    public List<KategoriPemasukan> getKategoriPemasukan(
        @PathVariable String workspaceId
    ){
        return kategoriPemasukanService.getKategoriPemasukanByWorkspace(workspaceId);
    }



    @PostMapping("/delete")
    @ResponseBody
    public String deleteKategoriPemasukanFromWorkspace(
        @PathVariable String workspaceId,
        @RequestParam String kategoriPemasukanId
    ){
        kategoriPemasukanService.deleteKategoriPemasukanFromWorkspace(workspaceId, kategoriPemasukanId);
        return success;
    }
}
