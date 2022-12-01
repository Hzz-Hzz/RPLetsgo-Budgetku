package xyz.rpletsgo.auth.repository;

import com.google.common.cache.Cache;
import org.junit.jupiter.api.Test;
import xyz.rpletsgo.auth.exceptions.InvalidSessionException;
import xyz.rpletsgo.auth.model.Pengguna;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SessionRepositoryTest {
    
    @Test
    void createSession() {
        Cache<String, Pengguna> cache = mock(Cache.class);
        var pengguna = mock(Pengguna.class);
        
        var sessionRepository = new SessionRepository();
        sessionRepository.setSessionToPenggunaMapping(cache);
        
        var randomToken = sessionRepository.createSession(pengguna);
        
        verify(cache, times(1)).put(randomToken, pengguna);
    }
    
    @Test
    void getSessionOrThrow_returnSuccessfully() {
        Cache<String, Pengguna> cache = mock(Cache.class);
        var pengguna = mock(Pengguna.class);
        when(cache.getIfPresent("a")).thenReturn(pengguna);
    
        var sessionRepository = new SessionRepository();
        sessionRepository.setSessionToPenggunaMapping(cache);
    
        var result = sessionRepository.getSessionOrThrow("a");
        assertSame(pengguna, result);
    }
    
    @Test
    void getSessionOrThrow_throwIfNotFound() {
        Cache<String, Pengguna> cache = mock(Cache.class);
        when(cache.getIfPresent("a")).thenReturn(null);
    
        var sessionRepository = new SessionRepository();
        sessionRepository.setSessionToPenggunaMapping(cache);
    
        assertThrows(InvalidSessionException.class,
                     () -> sessionRepository.getSessionOrThrow("a"));
    }
}