package xyz.rpletsgo.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.rpletsgo.auth.interceptor.LoginRequiredInterceptor;

@Configuration
public class LoginRequiredConfigurer implements WebMvcConfigurer {
    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor);
    }
}
