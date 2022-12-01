package xyz.rpletsgo.auth.controller;

import lombok.Generated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/")
public class PenggunaController {
    
    @Generated
    @GetMapping()
    public String mainPage() {
        return "Hello RPLetsgo!";
    }

}
