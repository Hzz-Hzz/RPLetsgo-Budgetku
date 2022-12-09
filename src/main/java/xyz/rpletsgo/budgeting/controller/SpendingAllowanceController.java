package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

import java.util.List;

@Controller
@RequestMapping("/{workspaceId}/spending-allowance")
public class SpendingAllowanceController {
    String success = "success";
    
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    
    
    @PostMapping("/create")
    @ResponseBody
    public SpendingAllowance createSpendingAllowance(
        @PathVariable String workspaceId,
        @RequestParam String nama
    ){
        return spendingAllowanceService.create(workspaceId, nama);
    }
    
    @PostMapping("/update")
    @ResponseBody
    public String updateSpendingAllowance(
        @PathVariable String workspaceId,
        @RequestParam String spendingAllowanceId,
        @RequestParam String nama
    ){
        spendingAllowanceService.update(workspaceId, spendingAllowanceId, nama);
        return success;
    }
    
    @GetMapping("/")
    @ResponseBody
    public List<SpendingAllowance> getSpendingAllowance(
        @PathVariable String workspaceId
    ){
        return spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
    }
    
    
    
    @PostMapping("/delete")
    @ResponseBody
    public String deleteSpendingAllowanceFromWorkspace(
        @PathVariable String workspaceId,
        @RequestParam String spendingAllowanceId
    ){
        spendingAllowanceService.deleteSpendingAllowanceFromWorkspace(workspaceId, spendingAllowanceId);
        return success;
    }
    
    @GetMapping("/list")
    public String getSpendingAllowancesList(@PathVariable String workspaceId, Model model){
        var spendingAllowances = spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("spendingAllowances", spendingAllowances);
        return "budgeting/spending-allowance-list.html";
    }
}
