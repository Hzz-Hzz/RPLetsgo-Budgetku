package xyz.rpletsgo.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.rpletsgo.auth.service.PenggunaService;

@Controller
@RequestMapping("/")
public class PenggunaController {
    @Autowired
    PenggunaService penggunaService;
    
    
    @Generated
    @ResponseBody
    @GetMapping()
    public String mainPage() {
        return "Hello RPLetsgo!";
    }
    
    
    @Generated
    @GetMapping("/login")
    public String getLogin() {
        return "/pengguna/login";
    }
    
    
    @Generated
    @ResponseBody
    @PostMapping("/login")
    public String postLogin(String username,
                            String password,
                            HttpServletResponse response) {
    
        var sessionToken = penggunaService.loginPengguna(username, password);
        var cookie = new Cookie("session", sessionToken);
        response.addCookie(cookie);
        return "Logged in!";
    }
    
    
    @Generated
    @GetMapping("/register")
    public String getRegister() {
        return "/pengguna/register";
    }
    
    @Generated
    @ResponseBody
    @PostMapping("/register")
    public String postRegister(
        String username,
        String email,
        String password
    ) {
        penggunaService.registerPengguna(username, email, password);
        return "Success!";
    }
}