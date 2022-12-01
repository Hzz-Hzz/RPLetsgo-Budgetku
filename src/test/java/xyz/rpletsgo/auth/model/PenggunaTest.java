package xyz.rpletsgo.auth.model;

import org.junit.jupiter.api.Test;
import xyz.rpletsgo.common.core.IPengguna;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PenggunaTest {
    static String username = "a";
    static String password = "b";
    static String name = "c";
    static String email = "d";
    
    @Test
    void constructorIsCorrect(){
        IPengguna pengguna = new Pengguna(username, password, email);
        
        assertEquals(username, pengguna.getUsername());
        assertEquals(password, pengguna.getPassword());
        assertEquals(email, pengguna.getEmail());
    }
    
    @Test
    void setterGetterRunsCorrectly(){
        IPengguna pengguna = new Pengguna("", "", "");
        
        pengguna.setPassword(password);
        pengguna.setEmail(email);
        
        assertEquals(password, pengguna.getPassword());
        assertEquals(email, pengguna.getEmail());
    }
    
    @Test
    void constructorRunsCorrectly(){
        assertDoesNotThrow(() -> new Pengguna());
    }
}