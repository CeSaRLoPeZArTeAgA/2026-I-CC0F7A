/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_IV_LENGTH;
import pe.edu.uni.fc.cc.common.Utils;

/**
 *
 * @author Usuario
 */
public class AESGCMCipherServiceTest {
    
    @Test
    public void testEncryptdecrypt(){
        byte[] key=new byte[16];
        byte[] iv=Utils.generateIV(AES_GCM_IV_LENGTH);
        byte[] aad="Header".getBytes();
        AESGCMCipherService aes_gcm=new AESGCMCipherService(key);
        String original="AES GCM Test";
        String encrypted=aes_gcm.encryptString(original, iv, aad);
        String decrypted=aes_gcm.decrypt(encrypted, aad);
        assertEquals(original,decrypted);    
    }
}
