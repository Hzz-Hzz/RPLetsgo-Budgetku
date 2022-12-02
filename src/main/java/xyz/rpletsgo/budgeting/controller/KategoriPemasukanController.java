package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.budgeting.service.KategoriPemasukanService;
import xyz.rpletsgo.pemasukan.model.KategoriPemasukan;

import java.util.List;

@Controller
@RequestMapping("/kategori-pemasukan")
public class KategoriPemasukanController {
    String success = "success";
    
    @Autowired
    KategoriPemasukanService kategoriPemasukanService;
    
    
    @PostMapping("/{workspaceId}/create")
    @ResponseBody
    public KategoriPemasukan createSpendingAllowance(
        @PathVariable String workspaceId,
        @RequestParam String nama,
        @RequestParam(value = "besarAlokasi[]") Double[] besarAlokasi,
        @RequestParam(value = "spendingAllowanceId[]") String[] spendingAllowanceId
    ){
        return kategoriPemasukanService.create(workspaceId, nama, besarAlokasi, spendingAllowanceId);
    }

    @PostMapping("/{workspaceId}/update")
    @ResponseBody
    public KategoriPemasukan updateSpendingAllowance(
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

    @GetMapping("/{workspaceId}")
    @ResponseBody
    public List<KategoriPemasukan> getKategoriPemasukan(
        @PathVariable String workspaceId
    ){
        return kategoriPemasukanService.getKategoriPemasukanByWorkspace(workspaceId);
    }


//
//    @PostMapping("/{workspaceId}/delete")
//    @ResponseBody
//    public String deleteSpendingAllowanceFromWorkspace(
//        @PathVariable String workspaceId,
//        @RequestParam String spendingAllowanceId
//    ){
//        spendingAllowanceService.deleteSpendingAllowanceFromWorkspace(workspaceId, spendingAllowanceId);
//        return success;
//    }
}
