package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.rpletsgo.budgeting.service.KategoriPemasukanService;

@Controller
@RequestMapping("/{workspaceId}/kategori-pemasukan")
public class KategoriPemasukanWebPageController {
    @Autowired
    KategoriPemasukanService kategoriPemasukanService;
    
    @GetMapping("/list")
    public String getKategoriPemasukanList(@PathVariable String workspaceId, Model model){
        var kategoriPemasukans = kategoriPemasukanService.getKategoriPemasukanByWorkspace(workspaceId);
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("kategoriPemasukans", kategoriPemasukans);
        return "budgeting/kategori-pemasukan-list.html";
    }
}
