/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.asymmetric.App;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import pe.edu.uni.fc.cc.asymmetric.Service.RSACipherService;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;

/**
 *
 * @author Usuario
 */
public class MainRSACipher {
    public static void main(String[] args) {
        System.out.println("Main RSA Cipher!!");
        
        //contenedor de llave publica
        KeyPair kp=null;
        
        try {
            //generacion del par de llaves
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            
            //tamanño de la llaves
            kpg.initialize(RSA_KEY_SIZE_2048);
            
            //genara el par de llaves y se guatrda en kp
            kp=kpg.generateKeyPair();
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(MainRSACipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        //instanciamos
        RSACipherService cipher =new RSACipherService(kp); 
       
        //mensaje plano
        String message="This is a message with RSA algorithm";
            
        //usando los los servicios
        String encryptedText=cipher.encrypt(message);
        String decryptedText=cipher.decrypt(encryptedText);
            
        //imprension de resultados
        System.out.println("Original text: " + message);
        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: "+decryptedText);  
    }
}
