package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

@Controller
@RequestMapping("/{workspaceId}/kategori-pemasukan")
public class KategoriPemasukanWebPageController {
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    
    @GetMapping("/list")
    public String getKategoriPemasukanList(@PathVariable String workspaceId, Model model){
        var spendingAllowances = spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("spendingAllowances", spendingAllowances);
        return "budgeting/kategori-pemasukan-list.html";
    }
}
