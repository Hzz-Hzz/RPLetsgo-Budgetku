package xyz.rpletsgo.auth.interceptor;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import xyz.rpletsgo.auth.exceptions.InvalidCredentialException;
import xyz.rpletsgo.auth.repository.SessionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    SessionRepository sessionRepository;
    
    List<String> authRequiredUrls = new ArrayList<>();
    
    @PostConstruct
    void defineAuthRequiredUrls(){
        authRequiredUrls.add("/logged-in");
    }
    
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        String uri = request.getRequestURI();
        if (!isAuthRequired(uri))
            return true;
    
        var sessionCookie = WebUtils.getCookie(request, "session");
        if (sessionCookie == null){
            throw new InvalidCredentialException("No login credential");
        }
        
        sessionRepository.getSessionOrThrow(sessionCookie.getValue());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    public boolean isAuthRequired(String uri){
        for (String authRequiredUrl: authRequiredUrls) {
            if (uri.startsWith(authRequiredUrl))
                return true;
        }
        return false;
    }
}
