package xyz.rpletsgo.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.rpletsgo.common.model.FinancialEventDTO;
import xyz.rpletsgo.common.service.CommonService;
import xyz.rpletsgo.pemasukan.model.Pemasukan;
import xyz.rpletsgo.pemasukan.service.PemasukanService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/{workspaceId}/transaction-list")
@RequiredArgsConstructor
public class TransactionListController {

    private final CommonService commonService;
    private final PemasukanService pemasukanService;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping(method = RequestMethod.GET, path = "")
    public String listTransaction(
            @PathVariable String workspaceId,
            Model model
    ) {
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("financialEvents",
                commonService.getWorkspaceFinancialEvents(workspaceId));
        model.addAttribute("dto", new FinancialEventDTO());
        return "common/transaction-list";

    }

    @PostMapping({""})
    public String editFinancialEvent(
            FinancialEventDTO dto,
            @PathVariable String workspaceId) {
        var waktuLocalDateTime = LocalDate.parse(dto.getWaktu(), formatter).atStartOfDay();
        switch(dto.getType()){
            case "Pemasukan":
                pemasukanService.update(
                        workspaceId,
                        dto.getId(),
                        dto.getNama(),
                        dto.getKeterangan(),
                        waktuLocalDateTime,
                        dto.getNominal(),
                        dto.getKategoriId()
                );
                break;
            case "Pengeluaran":
                break;
            case "Tagihan":
                break;
        }
        return "common/transaction-list";
    }
}
