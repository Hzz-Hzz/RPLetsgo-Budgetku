package xyz.rpletsgo.tagihan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.service.TagihanService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/{workspaceId}/tagihan")
public class TagihanController {
    @Autowired
    TagihanService tagihanService;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @PostMapping("/create")
    @ResponseBody
    public String createTagihan(
            @PathVariable String workspaceId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam String waktuStr,
            @RequestParam long nominal
            ){
        LocalDateTime waktu = LocalDate.parse(waktuStr, formatter).atStartOfDay();
        tagihanService.create(workspaceId, nama, keterangan, waktu, nominal);
        return "success";
    }

    @PostMapping("/update/{tagihanId}")
    @ResponseBody
    public String updateTagihan(
            @PathVariable String workspaceId,
            @PathVariable String tagihanId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam String waktuStr,
            @RequestParam long nominal
    ){
        LocalDateTime waktu = LocalDate.parse(waktuStr, formatter).atStartOfDay();
        tagihanService.update(workspaceId, tagihanId, nama, keterangan, waktu, nominal);
        return "success";
    }

    @PostMapping("/delete/{tagihanId}")
    @ResponseBody
    public String deleteTagihan(
            @PathVariable String workspaceId,
            @PathVariable String tagihanId
    ){
        tagihanService.deleteTagihan(workspaceId, tagihanId);
        return "success";
    }

    @GetMapping("/getid/{tagihanId}")
    @ResponseBody
    public FinancialEvent getTagihan(
            @PathVariable String workspaceId,
            @PathVariable String tagihanId
    ){
        return tagihanService.getTagihan(workspaceId, tagihanId);
    }

    @GetMapping("/getlist")
    @ResponseBody
    public List<FinancialEvent> getTagihan(
            @PathVariable String workspaceId
    ){
        return tagihanService.getListTagihan(workspaceId);
    }
}
