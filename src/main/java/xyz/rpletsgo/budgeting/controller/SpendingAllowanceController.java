package xyz.rpletsgo.budgeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.rpletsgo.budgeting.model.SpendingAllowance;
import xyz.rpletsgo.budgeting.service.SpendingAllowanceService;

import java.util.List;

@Controller
@RequestMapping("/spending-allowance")
public class SpendingAllowanceController {
    @Autowired
    SpendingAllowanceService spendingAllowanceService;
    
    
    @PostMapping("/create")
    @ResponseBody
    public String createSpendingAllowance(
        @RequestParam String workspaceId,
        @RequestParam String nama
    ){
        spendingAllowanceService.create(workspaceId, nama);
        return "success";
    }
    
    @PostMapping("/update")
    @ResponseBody
    public String updateSpendingAllowance(
        @RequestParam String workspaceId,
        @RequestParam String spendingAllowanceId,
        @RequestParam String newName
    ){
        spendingAllowanceService.update(workspaceId, spendingAllowanceId, newName);
        return "success";
    }
    
    @PostMapping("/update")
    @ResponseBody
    public List<SpendingAllowance> getSpendingAllowance(
        @RequestParam String workspaceId
    ){
        return spendingAllowanceService.getSpendingAllowancesByWorkspace(workspaceId);
    }
}
