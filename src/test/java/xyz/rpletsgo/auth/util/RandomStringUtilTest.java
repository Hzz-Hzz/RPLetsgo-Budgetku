package xyz.rpletsgo.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomStringUtilTest {
    
    @Test
    void testRandomString() {
        var result1 = RandomStringUtil.randomString(7);
        var result2 = RandomStringUtil.randomString(11);
        var result3 = RandomStringUtil.randomString(23);
        
        var result4_allowedCharacters = "ab";
        var result4 = RandomStringUtil.randomString(23, result4_allowedCharacters);
        
        assertEquals(7, result1.length());
        assertEquals(11, result2.length());
        assertEquals(23, result3.length());
        assertEquals(23, result4.length());
        
        assertNotEquals(result3, result4);
        
        for (int i = 0; i < result4.length(); i++) {
            char currChar = result4.charAt(i);
            String currCharAsString = Character.toString(currChar);
            assertTrue(result4_allowedCharacters.contains(currCharAsString));
        }
    }
}