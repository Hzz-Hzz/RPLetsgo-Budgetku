package xyz.rpletsgo.auth.interceptor;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestMapping("/handler")
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    SessionRepository sessionRepository;
    
    @Getter
    @Setter
    List<String> urlExceptions = new ArrayList<>();
    
    @Autowired
    CurrentLoggedInPengguna loggedInPengguna;
    
    @Generated
    @PostConstruct
    void defineAuthRequiredUrls(){
        urlExceptions.add("/");
        urlExceptions.add("/login");
        urlExceptions.add("");
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        var uri = request.getRequestURI();
        if (!isAuthRequired(uri))
            return true;
        
        var sessionCookie = WebUtils.getCookie(request, "session");
        if (sessionCookie == null){
            return true;
        }
        
        var pengguna = sessionRepository.getSessionOrThrow(sessionCookie.getValue());
        loggedInPengguna.setCurrentPengguna(pengguna);
        
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    public boolean isAuthRequired(String uri){
        for (String authRequiredUrl: urlExceptions) {
            if (uri.equals(authRequiredUrl))
                return false;
        }
        return true;
    }
}
