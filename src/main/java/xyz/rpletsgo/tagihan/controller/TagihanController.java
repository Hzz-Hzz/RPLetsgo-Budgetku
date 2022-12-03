package xyz.rpletsgo.tagihan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.common.model.FinancialEvent;
import xyz.rpletsgo.tagihan.service.TagihanService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/{workspaceId}/tagihan")
public class TagihanController {
    @Autowired
    TagihanService tagihanService;

    @PostMapping("/create")
    @ResponseBody
    public String createTagihan(
            @PathVariable String workspaceId,
            @RequestParam String nama,
            @RequestParam String keterangan,
            @RequestParam LocalDateTime waktu,
            @RequestParam long nominal
            ){
        tagihanService.create(workspaceId, nama, keterangan, waktu, nominal);
        return "success";
    }

    @PostMapping("/update/{tagihanId}")
    @ResponseBody
    public String updateTagihan(
            @PathVariable String workspaceId,
            @PathVariable String tagihanId,
            @RequestParam String name,
            @RequestParam String keterangan,
            @RequestParam LocalDateTime waktu,
            @RequestParam long nominal
    ){
        tagihanService.update(workspaceId, tagihanId, name, keterangan, waktu, nominal);
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

    @GetMapping("/getid")
    @ResponseBody
    public FinancialEvent getTagihan(
            @PathVariable String workspaceId,
            @RequestParam String tagihanId
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
