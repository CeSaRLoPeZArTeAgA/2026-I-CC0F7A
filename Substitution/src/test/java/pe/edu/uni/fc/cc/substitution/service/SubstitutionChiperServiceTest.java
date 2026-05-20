/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Usuario
 */
public class SubstitutionChiperServiceTest {
    //llave
    static char[] key={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
    SubstitutionChiperService chiper= new SubstitutionChiperService(key);
    
    @Test
    public void testEncryptDecrypIdentity(){
        String input="Hello world! chiper";
        String encrypted=chiper.encrypt(input);
        String decrypted=chiper.decrypt(encrypted);
        assertEquals(input,decrypted);
    }
    
}
