/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.asymmetric.Service;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;

/**
 *
 * @author Usuario
 */
public class RSACipherService {
    
    private final PublicKey publickey;
    private final PrivateKey privateKey;
    
    public RSACipherService(KeyPair kp){
        this.publickey=kp.getPublic();
        this.privateKey=kp.getPrivate();
    }
    
    
    public RSACipherService(PublicKey pubKey){
        this.publickey=pubKey;
        this.privateKey=null;
    }
    
    public RSACipherService(PrivateKey priKey){
        this.publickey=null;
        this.privateKey=priKey;
    }
    
    public byte[] encrypt(byte[] plainText){
        
        if(publickey==null) return null;
       
        
        //String result="";
        byte[] result=null;
        
        //instanciamos el cifrador
        
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            
            //inicializando el cifrador
            cipher.init(Cipher.ENCRYPT_MODE, publickey);
            
            //guardar el cifrado
            //byte[] encryptedText=cipher.doFinal(plainText.getBytes());
            byte[] encryptedText=cipher.doFinal(plainText);
            
            //result=Base64.getEncoder().encodeToString(encryptedText);
            result=encryptedText;
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            
        
        
        
        return result;
    }
    
    
    public String encrypt(String plainText){
        byte[] encryptedText=encrypt(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedText);
    }
    
    
    public byte[] decrypt(byte[] encryptedText){
        
        if(privateKey==null) return null;
         byte[] result=null; 
        
        
        //String result="";
        
             
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            //
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            
            //
            //byte[] decodeEncryptedText=Base64.getDecoder().decode(encryptedText);
            byte[] decodeEncryptedText=encryptedText;
            
            //descifrado
            byte[] decryptedText=cipher.doFinal(decodeEncryptedText);
            
            //result=new String(decryptedText);
            result=decryptedText;
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(RSACipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            
            
        
        return result;
    }
    
    
     public String decrypt(String encryptedText){
         byte[] decodeEncryptedText=Base64.getDecoder().decode(encryptedText);
         byte[] decryptedText=decrypt(decodeEncryptedText);
         return new String(decryptedText);
     }
}
