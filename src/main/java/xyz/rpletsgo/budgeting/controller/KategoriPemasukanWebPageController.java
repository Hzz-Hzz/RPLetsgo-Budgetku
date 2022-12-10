package xyz.rpletsgo.budgeting.controller;

import com.google.gson.Gson;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.rpletsgo.budgeting.service.KategoriPemasukanService;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@Generated
@RequestMapping("/{workspaceId}/kategori-pemasukan")
public class KategoriPemasukanWebPageController {
    @Autowired
    KategoriPemasukanService kategoriPemasukanService;
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    
    @GetMapping("/list")
    public String getKategoriPemasukanList(@PathVariable String workspaceId, Model model){
        var kategoriPemasukans = kategoriPemasukanService.getKategoriPemasukanByWorkspace(workspaceId);
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("kategoriPemasukans", kategoriPemasukans);
        return "budgeting/kategori-pemasukan-list.html";
    }
    
    @GetMapping("/create-edit")
    public String createOrEditPemasukan(
            @RequestParam Optional<String> kategoriId,
            @PathVariable String workspaceId,
            Model model){
    
        model.addAttribute("workspaceId", workspaceId);
        var allSpendingAllowances = spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
        model.addAttribute("spendingAllowancesJson",
                           new Gson().toJson(allSpendingAllowances)
        );
        
        if (kategoriId.isPresent()){
            var kategoriPemasukan = kategoriPemasukanService.getKategoriPemasukan(workspaceId, kategoriId.get());
            var alokasi = kategoriPemasukan.getAlokasiSpendingAllowances();
            
            
            model.addAttribute("kategoriName", kategoriPemasukan.getNama());
            model.addAttribute("kategoriId", kategoriPemasukan.getId());
            model.addAttribute("alokasiAllowances", alokasi);
            model.addAttribute("alokasiAllowancesJson",
                               new Gson().toJson(alokasi)
            );
            
            
            model.addAttribute("isKategoriDefined", true);
        }else{
            model.addAttribute("alokasiAllowancesJson",
                               new Gson().toJson(new ArrayList())
            );
            model.addAttribute("isKategoriDefined", false);
        }
        
        return "budgeting/kategori-pemasukan-create-update.html";
    }
}
