package xyz.rpletsgo.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.rpletsgo.auth.exceptions.InvalidCredentialException;
import xyz.rpletsgo.auth.exceptions.UsernameAlreadyExistsException;
import xyz.rpletsgo.auth.exceptions.UsernameNotFoundException;
import xyz.rpletsgo.auth.model.Pengguna;
import xyz.rpletsgo.auth.repository.PenggunaRepository;
import xyz.rpletsgo.auth.repository.SessionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PenggunaServiceTest {
    @MockBean
    PenggunaRepository penggunaRepository;
    
    @MockBean
    SessionRepository sessionRepository;
    
    @InjectMocks
    PenggunaService penggunaService;
    
    Pengguna pengguna1;
    String password = "b";
    
    @BeforeEach
    void setUp() {
        pengguna1 = mock(Pengguna.class);
        when(pengguna1.getUsername()).thenReturn("a");
        when(pengguna1.getPassword()).thenReturn(password);
        
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getPengguna_returnedSuccessfully() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.of(pengguna1));
    
        var pengguna = penggunaService.getPengguna("a");
        
        assertSame(pengguna1, pengguna);
    }
    
    @Test
    void getPengguna_returnedNullIfNotFound() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.empty());
        
        var pengguna = penggunaService.getPengguna("a");
        
        assertNull(pengguna);
    }
    
    
    
    @Test
    void getPenggunaOrThrow_returnedSuccessfully() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.of(pengguna1));
    
        var pengguna = penggunaService.getPenggunaOrThrow("a");
    
        assertSame(pengguna1, pengguna);
    }
    
    @Test
    void getPenggunaOrThrow_throwIfNotFound() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.empty());
    
        assertThrows(UsernameNotFoundException.class,
                     () -> penggunaService.getPenggunaOrThrow("a"));
    }
    
    
    
    
    @Test
    void loginPengguna_returnedSuccessfully() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.of(pengguna1));
        when(sessionRepository.createSession(any())).thenReturn("session");
        
        var session = penggunaService.loginPengguna("a", password);
        
        assertEquals("session", session);
        verify(sessionRepository, times(1)).createSession(pengguna1);
    }
    
    @Test
    void loginPengguna_throwIfUsernameNotFound() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class,
                     () -> penggunaService.loginPengguna("a", password));
    }
    
    
    @Test
    void loginPengguna_throwIfPasswordWrong() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.of(pengguna1));
        
        assertThrows(InvalidCredentialException.class,
                     () -> penggunaService.loginPengguna("a", "c"));
    }
    
    
    
    @Test
    void registerPengguna_throwIfUsernameAlreadyExist() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.of(pengguna1));
        
        assertThrows(UsernameAlreadyExistsException.class,
                     () -> penggunaService.registerPengguna("a", "b", "c"));
    }
    
    @Test
    void registerPengguna_saveToDatabaseIfSuccess() {
        when(penggunaRepository.findByUsername("a")).thenReturn(Optional.empty());
        
        penggunaService.registerPengguna("a", "b", "c");
    
        var penggunaCaptor = ArgumentCaptor.forClass(Pengguna.class);
        verify(penggunaRepository, times(1)).save(penggunaCaptor.capture());
    
        var savedPengguna = penggunaCaptor.getValue();
        assertSame("a", savedPengguna.getUsername());
        assertSame("b", savedPengguna.getEmail());
        assertSame("c", savedPengguna.getPassword());
    }
}