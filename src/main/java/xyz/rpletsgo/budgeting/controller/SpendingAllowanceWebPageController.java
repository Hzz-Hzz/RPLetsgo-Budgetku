package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

@Controller
@RequestMapping("/{workspaceId}/spending-allowance")
public class SpendingAllowanceWebPageController {
    String success = "success";
    
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    
    @GetMapping("/list")
    public String getSpendingAllowancesList(@PathVariable String workspaceId, Model model){
        var spendingAllowances = spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("spendingAllowances", spendingAllowances);
        return "budgeting/spending-allowance-list.html";
    }
    
    @GetMapping("/create")
    public String getSpendingAllowancesList(@PathVariable String workspaceId){
        spendingAllowanceService.authorizeWorkspace(workspaceId);
        return "budgeting/spending-allowance-create.html";
    }
}
