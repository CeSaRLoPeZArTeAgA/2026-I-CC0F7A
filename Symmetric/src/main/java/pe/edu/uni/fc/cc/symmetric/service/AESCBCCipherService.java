/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.AES_CBC_IV_LENGTH;
import static pe.edu.uni.fc.cc.common.Constants.TRANSFOMATION_AES_CBC;
import pe.edu.uni.fc.cc.symmetric.AESCBCCipher;
import pe.edu.uni.fc.cc.symmetric.exception.CryptoExceptionHandler;

/**
 *
 * @author Usuario
 */
public class AESCBCCipherService {
    private final byte[] key;
    public AESCBCCipherService(byte[] key){
        this.key=key;
    }
    
    public String encrypt(byte[] initVector, String plainText){
    
    String result="";
        
        //Vector de Inicializacion pasada a la clase de java  IvParameterSpec
        IvParameterSpec iv= new IvParameterSpec(initVector);
        
        //la llave pasada a la clase de java SecretKeySpec
        SecretKeySpec sKeySpec= new SecretKeySpec(key,AES_ALGORITHM);
        
        try {
            //instanciando el cifrador AES
            Cipher cipher=Cipher.getInstance(TRANSFOMATION_AES_CBC);
            
            //parametros del cifrador, inicilizacion del cifrador
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,iv);
            
            //encriptacion del texto plano, se tiene que llevarlo a byte en formato UTF 8
            byte[] encrypted=cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            
            //[iv,"texto cifrado"]
            byte[] combined=new byte[initVector.length + encrypted.length];
            System.arraycopy(initVector, 0, combined, 0, initVector.length);
            System.arraycopy(encrypted, 0, combined, initVector.length, encrypted.length);
            
            //el resultado de encrypted en array de bytes no se puede visualizar, asi que se pasa a base 64 para observalo
            result=Base64.getEncoder().encodeToString(combined);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
           // System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
           throw CryptoExceptionHandler.handle(ex);
        }
        
        return result;
        
    }
    
    public String decrypt(String encryptedText){
        
        String result="";
        
        byte[] combined= Base64.getDecoder().decode(encryptedText);
        byte[] initVector=new byte[AES_CBC_IV_LENGTH];
        byte[] cipheredText=new byte[combined.length-initVector.length];
        System.arraycopy(combined,0,initVector,0, initVector.length);
        System.arraycopy(combined,initVector.length,cipheredText,0,cipheredText.length);
        
        //Vector de Inicializacion pasada a la clase de java IvParameterSpec
        IvParameterSpec iv=new IvParameterSpec(initVector);
        
        //la llave pasada a la clase de java SecretKeySpec
        SecretKeySpec sKeySpec=new SecretKeySpec(key,AES_ALGORITHM);
        
        try {
            //instanciando el cifrador AES
            Cipher cipher= Cipher.getInstance(TRANSFOMATION_AES_CBC);
            
            //parametros del decifrador, inicilizacion del decifrador
            cipher.init(Cipher.DECRYPT_MODE,sKeySpec,iv);
            
            //desencriptado el cual previamente tenemos de decoficarlo de base 64, para aplicarle AES
            byte[] decrypted= cipher.doFinal(cipheredText);
            
            //convertimos el desencriptado a una cadena visible con UTF 8
            result= new String(decrypted,StandardCharsets.UTF_8);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            //System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw CryptoExceptionHandler.handle(ex);
        }
        
        return result; 
    }
    
}
