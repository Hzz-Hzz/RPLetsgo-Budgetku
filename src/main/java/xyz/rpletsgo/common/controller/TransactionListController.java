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
import xyz.rpletsgo.pengeluaran.service.PengeluaranService;
import xyz.rpletsgo.tagihan.service.TagihanService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/{workspaceId}/transaction-list")
@RequiredArgsConstructor
public class TransactionListController {
    @Autowired
    private final CommonService commonService;

    @Autowired
    private final PemasukanService pemasukanService;

    @Autowired
    private final PengeluaranService pengeluaranService;

    @Autowired
    private final TagihanService tagihanService;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping(method = RequestMethod.GET, path = "")
    public String listTransaction(
            @PathVariable String workspaceId,
            Model model
    ) {
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("kategoriPemasukans",
                commonService.getWorkspaceKategoriPemasukan(workspaceId));
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
                pengeluaranService.update(
                        workspaceId,
                        dto.getId(),
                        dto.getNama(),
                        dto.getKeterangan(),
                        waktuLocalDateTime,
                        dto.getNominal()
                );
                break;
            case "Tagihan":
                tagihanService.update(
                        workspaceId,
                        dto.getId(),
                        dto.getNama(),
                        dto.getKeterangan(),
                        waktuLocalDateTime,
                        dto.getNominal()
                );
                break;
        }
        return "redirect:/"+workspaceId+"/transaction-list";
    }
}
