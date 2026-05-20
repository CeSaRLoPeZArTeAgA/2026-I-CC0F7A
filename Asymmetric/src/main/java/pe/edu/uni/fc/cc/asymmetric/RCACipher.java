/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.asymmetric;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;

/**
 *
 * @author Usuario
 */
public class RCACipher {

    public static void main(String[] args) {
        System.out.println("RCA Cipher!!!");
        try {
            //genetracion de la llaves RSA
            KeyPairGenerator kpg= KeyPairGenerator.getInstance(RSA_ALGORITHM);
            
            //tamanño de la llaves
            kpg.initialize(RSA_KEY_SIZE_2048);
            
            //genara el par de llaves y se guatrda en kp
            KeyPair kp=kpg.generateKeyPair();
            
            //obtener el par de llaver
            PublicKey pubKey=kp.getPublic();
            PrivateKey priKey=kp.getPrivate();
            
            //impresion que ya se genero las llaves ok
            System.out.println("Key Pair generated!!!");
            
            //mensaje plano
            String message="This is a message with RSA algorithm";
            
            //
            String encryptedText=encrypt(pubKey,message);
            String decryptedText=decrypt(priKey,encryptedText);
            
            //imprension de resultados
            System.out.println("Original text: " + message);
            System.out.println("Encrypted text: " + encryptedText);
            System.out.println("Decrypted text: "+decryptedText);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    
    private static String encrypt(PublicKey pubKey,String plainText){
        String result="";
        try {
            //instanciamos el cifrador
            Cipher cipher=Cipher.getInstance(RSA_ALGORITHM);
            
            //inicializando el cifrador
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            
            //guardar el cifrado
            byte[] encryptedText=cipher.doFinal(plainText.getBytes());
            
            result=Base64.getEncoder().encodeToString(encryptedText);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return result;
    }
    
    
    private static String decrypt(PrivateKey priKey, String encryptedText){
        String result="";
        
        try {
            //cifrador
            Cipher cipher=Cipher.getInstance(RSA_ALGORITHM);
            
            //
            cipher.init(Cipher.DECRYPT_MODE,priKey);
            
            //
            byte[] decodeEncryptedText=Base64.getDecoder().decode(encryptedText);
            
            //descifrado
            byte[] decryptedText=cipher.doFinal(decodeEncryptedText);
            
            result=new String(decryptedText);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(RCACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
                
        return result;
    }
    
}
