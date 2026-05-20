/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
import pe.edu.uni.fc.cc.common.Utils;
import static pe.edu.uni.fc.cc.common.Utils.generateIV;

/**
 *
 * @author Usuario
 */
public class AESCBCCipher {
    
    public static String encrypt(byte[] key,byte[] initVector, String plainText){
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
            
            //el resultado de encrypted en array de bytes no se puede visualizar, asi que se pasa a base 64 para observalo
            result=Base64.getEncoder().encodeToString(encrypted);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return result;
    }
    
    public static String decrypt(byte[] key,byte[] initVector, String cipheredText){
        String result="";
        
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
            byte[] decrypted= cipher.doFinal(Base64.getDecoder().decode(cipheredText));
            
            //convertimos el desencriptado a una cadena visible con UTF 8
            result= new String(decrypted,StandardCharsets.UTF_8);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(AESCBCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("Symmetric AES CBC!!");
        
        //fuente de seudo aleatoridad
        SecureRandom sr=new SecureRandom();
        
        //contenedor de la llave AES
        byte[] key=new byte[16];//128 bits -> 16 byte=128/8
        //generacion de la llave AES, necesita cadena aleatoria del objeto sr
        sr.nextBytes(key);
        //visualizacion de llave en hexadecimal
        System.out.println("key: "+Utils.byteToHex(key));
        
        //contenedor del vector de inicializacion IV (Inition Vector), tiene que tener la longitud de la llave
        //byte[] initVector=new byte[16];
        //generacion del vector de inicializacion IV
        //sr.nextBytes(initVector);
        //generacion del vector de inicializacion con programa de Utils
        byte[] initVector=Utils.generateIV(AES_CBC_IV_LENGTH);
        //visualizacion del vector de inicializacion
        System.out.println("Vector Inicializacion: "+Utils.byteToHex(initVector));
            
        //texto plano a cifrar
        String payload="This is a plaintext sent from ALice to Bod.";
        
        //texto encriptado
        String encrypted=encrypt(key, initVector,payload);
        
        //texto desencriptado
        String decrypted=decrypt(key, initVector,encrypted);
        
        //impresion de los texto original, encriptado y desencriptado
        System.out.println("Texto Original: "+payload);
        System.out.println("Texto cifrado: "+encrypted);
        System.out.println("Texto decifrado: "+decrypted);
        
        //compara la cadena original con la cadena desencriptada. Si sale bien es OK, si sale mal KO
        String result=decrypted.equals(payload)?"OK":"KO!";
        //impresion de la comparacion de las cadenas
        System.out.println("Iguales? "+result);      
    }
}
