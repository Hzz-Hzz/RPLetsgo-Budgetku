package xyz.rpletsgo.auth.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStringUtil {
    private RandomStringUtil(){}
    
    static final String ALPHABET_AND_NUMBER =
              "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789";
    
    public static String randomString(int length){
        final String ALLOWED_CHARACTERS = ALPHABET_AND_NUMBER;
        return randomString(length, ALLOWED_CHARACTERS, new SecureRandom());
    }
    
    public static String randomString(int length, String allowedCharacters){
        return randomString(length, allowedCharacters, new SecureRandom());
    }
    
    public static String randomString(int length, Random randomObj){
        final String ALLOWED_CHARACTERS = ALPHABET_AND_NUMBER;
        return randomString(length, ALLOWED_CHARACTERS, randomObj);
    }
    
    public static String randomString(int length, String allowedCharacters, Random randomObj){
        var ret = new StringBuilder();
        
        for (var i = 0; i < length; i++) {
            var index = randomObj.nextInt(allowedCharacters.length());
            var resultingChar = allowedCharacters.charAt(index);
            ret.append(resultingChar);
        }
        
        return ret.toString();
    }
}
