package xyz.rpletsgo.common.controller;

import lombok.Generated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/")
public class MainController {
    
    @Generated
    @GetMapping(produces = "text/html;charset=UTF-8")
    public String mainPage() {
        return "Hello RPLetsgo!";
    }

}
