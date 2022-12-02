package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

import java.util.List;

@Controller
@RequestMapping("/spending-allowance")
public class SpendingAllowanceController {
    String success = "success";
    
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    
    
    @PostMapping("/{workspaceId}/create")
    @ResponseBody
    public SpendingAllowance createSpendingAllowance(
        @PathVariable String workspaceId,
        @RequestParam String nama
    ){
        return spendingAllowanceService.create(workspaceId, nama);
    }
    
    @PostMapping("/{workspaceId}/update")
    @ResponseBody
    public String updateSpendingAllowance(
        @PathVariable String workspaceId,
        @RequestParam String spendingAllowanceId,
        @RequestParam String nama
    ){
        spendingAllowanceService.update(workspaceId, spendingAllowanceId, nama);
        return success;
    }
    
    @GetMapping("/{workspaceId}")
    @ResponseBody
    public List<SpendingAllowance> getSpendingAllowance(
        @PathVariable String workspaceId
    ){
        return spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
    }
    
    
    
    @PostMapping("/{workspaceId}/delete")
    @ResponseBody
    public String deleteSpendingAllowanceFromWorkspace(
        @PathVariable String workspaceId,
        @RequestParam String spendingAllowanceId
    ){
        spendingAllowanceService.deleteSpendingAllowanceFromWorkspace(workspaceId, spendingAllowanceId);
        return success;
    }
}
