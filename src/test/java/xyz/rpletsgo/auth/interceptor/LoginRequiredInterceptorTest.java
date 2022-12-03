package xyz.rpletsgo.auth.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import xyz.rpletsgo.auth.component.CurrentLoggedInPengguna;
import xyz.rpletsgo.auth.exceptions.InvalidSessionException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.repository.SessionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginRequiredInterceptorTest {
    SessionRepository sessionRepository;
    CurrentLoggedInPengguna currentPengguna;
    
    @InjectMocks
    LoginRequiredInterceptor loginRequiredInterceptor;
    
    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        currentPengguna = mock(CurrentLoggedInPengguna.class);
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @SneakyThrows
    void preHandle_returnTrueIfUrlDoesNotRequireLogin() {
        assertTrue(
            testPreHandle("/login-not-required", new Cookie[0])
        );
    }
    
    @Test
    @SneakyThrows
    void preHandle_throwIfSessionNotInRepository() {
        when(sessionRepository.getSessionOrThrow("a")).thenThrow(InvalidSessionException.class);
        var cookie = new Cookie("session", "a");
        
        assertThrows(
            InvalidSessionException.class,
            () -> testPreHandle(
                    "/",
                    new Cookie[]{cookie}
            )
        );
    }
    
    @Test
    void preHandle_returnTrueIfSessionValid() {
        var pengguna = mock(Pengguna.class);
        when(sessionRepository.getSessionOrThrow("a")).thenReturn(pengguna);
        
        assertTrue(
            testPreHandle(
                "/",
                new Cookie[]{
                    new Cookie("session", "a")
                }
            )
        );
        verify(currentPengguna, times(1)).setCurrentPengguna(pengguna);
    }
    
    @Test
    void preHandle_returnTrueIfUrlDoesntNeedLogin() {
        var pengguna = mock(Pengguna.class);
        when(sessionRepository.getSessionOrThrow("a")).thenReturn(pengguna);
    
        assertTrue(
            testPreHandle(
                "/login-not-required",
                new Cookie[]{
                    new Cookie("session", "a")
                }
            )
        );
    }
    
    
    boolean testPreHandle(String uri, Cookie[] cookieArr){
        var servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn(uri);
        
        when(servletRequest.getCookies()).thenReturn(
            cookieArr
        );
        
        var whiteListUrls = List.of("/login-not-required");
        loginRequiredInterceptor.setUrlExceptions(whiteListUrls);
        
        try {
            return loginRequiredInterceptor.preHandle(servletRequest, null, null);
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void isAuthRequired() {
        // login-required urls
        var loginRequiredUrls = List.of(
            "/a",
            "/b/c"
        );
        loginRequiredInterceptor.setUrlExceptions(loginRequiredUrls);
        
        assertFalse(loginRequiredInterceptor.isAuthRequired("/a"));
        assertFalse(loginRequiredInterceptor.isAuthRequired("/b/c"));
    
        assertTrue(loginRequiredInterceptor.isAuthRequired("/a/b"));
        assertTrue(loginRequiredInterceptor.isAuthRequired("/b"));
        assertTrue(loginRequiredInterceptor.isAuthRequired("/c"));
    }
}