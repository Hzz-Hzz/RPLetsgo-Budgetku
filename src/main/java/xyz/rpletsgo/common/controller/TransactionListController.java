package xyz.rpletsgo.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.rpletsgo.common.service.CommonService;

@Controller
@RequestMapping("/{workspaceId}/transaction-list")
public class TransactionListController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public String listTransaction(
            @PathVariable String workspaceId,
            Model model
    ) {
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("financialEvents",
                commonService.getWorkspaceFinancialEvents(workspaceId));
        return "common/transaction-list";

    }
}
