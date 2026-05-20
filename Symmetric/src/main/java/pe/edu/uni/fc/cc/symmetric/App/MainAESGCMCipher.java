/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.App;

import java.security.SecureRandom;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_ADD;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_IV_LENGTH;
import pe.edu.uni.fc.cc.common.Utils;
import pe.edu.uni.fc.cc.symmetric.service.AESGCMCipherService;

/**
 *
 * @author Usuario
 */
public class MainAESGCMCipher {
    public static void main(String[] args) {
        System.out.println("Main AES GCM Cipher!!!!");
        
        //contenedor de la llave
        byte[] key=new byte[16];
        
        //genera aleatoriamente la llave
        new SecureRandom().nextBytes(key);
        
        //crea el objeto aes_gcm
        AESGCMCipherService aes_gcm=new AESGCMCipherService(key);
        
        //generacion del vector inicial
        byte[] iv=Utils.generateIV(AES_GCM_IV_LENGTH);
        
        //dato de autenticacion, para verificar que el cifrado ha sido generado por el
        //byte[] aad="Header".getBytes();
        
        //texto a cifrar
        String msg="This is a message!!!";
        
        //encripta mensaje
        String encrypted=aes_gcm.encryptString(msg, iv, AES_GCM_ADD);
        
        //desencripta mensaje
        String decrypted=aes_gcm.decrypt(encrypted, AES_GCM_ADD);
        
        //impresion de resultados
        System.out.println("Original: "+msg);
        System.out.println("Encrypted: "+encrypted);
        System.out.println("Decryped: "+decrypted);
        
    }
}
