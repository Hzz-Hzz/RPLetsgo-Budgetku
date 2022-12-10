package xyz.rpletsgo.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.rpletsgo.common.service.CommonService;

import java.util.Map;

@Controller
@RequestMapping("/{workspaceId}/overview")
public class OverviewController {
    @Autowired
    CommonService commonService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public String listTotalSummary(
            @PathVariable String workspaceId,
            Model model
    ){
        Map<String, String> totalFinancialSummary = commonService.getTotalFinancial(workspaceId);
        model.addAttribute("pemasukan", totalFinancialSummary.get("pemasukan"));
        model.addAttribute("pengeluaran", totalFinancialSummary.get("pengeluaran"));
        model.addAttribute("tagihan", totalFinancialSummary.get("tagihan"));
        model.addAttribute("saldo", totalFinancialSummary.get("saldo"));
        model.addAttribute("workspaceId", workspaceId);
        return "common/overview";
    }
}
