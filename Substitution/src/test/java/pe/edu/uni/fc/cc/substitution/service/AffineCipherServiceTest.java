/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static pe.edu.uni.fc.cc.common.Constants.ALPHABET_SET_SIZE;
import static pe.edu.uni.fc.cc.common.Utils.gcd;

import static pe.edu.uni.fc.cc.common.Utils.getInverseModule;

/**
 *
 * @author Usuario
 */
public class AffineCipherServiceTest {
    
    private final AffineChiperService cipher= new AffineChiperService(11,6);
    
    @Test
    public void testEncryptDecryptIdentity(){
        String input="Hello world!";
        String encrypted=cipher.encrypt(input);
        String decrypted=cipher.decrypt(encrypted);
        assertEquals(input,decrypted);
    }
    
    @Test
    public void testInvalidkey(){
        //compara excepciones
        assertThrows(IllegalArgumentException.class,()->{
            new AffineChiperService(13,5);
        });
    }
    
    @Test
    public void testgetInverseModule(){
        int a=11;
        int inv_a=19;
        //compara el inverso modular de a, modulo 26, con inv_a
        assertEquals(inv_a,getInverseModule(a,ALPHABET_SET_SIZE));
    }
    
    @Test
    public void testgcd(){
        int a=11;
        //mcd(11,26)=1
        assertEquals(1,gcd(a,ALPHABET_SET_SIZE));
    }
    
    @Test
    public void testInverseModule2(){
        int a=11;
        //a*inv_a=1 mod 26      
        assertEquals(1,(a*getInverseModule(a,ALPHABET_SET_SIZE))%ALPHABET_SET_SIZE);
    }
  
}
