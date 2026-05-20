/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.asymmetric.App;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;
import pe.edu.uni.fc.cc.asymmetric.Service.ECCCipherService;
import static pe.edu.uni.fc.cc.common.Constants.ECC_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.ECC_KEY_PARAMETER_SPEC;

/**
 *
 * @author Usuario
 */
public class MainECCCipher {
    public static void main(String[] args) {
        System.out.println("MainECCCipher!!");  
        KeyPair kp=null;
        try {   
            //inicializacion del generador del algortimo
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ECC_ALGORITHM);
            //configuracion de los parametros del algoritmo
            kpg.initialize(new ECGenParameterSpec(ECC_KEY_PARAMETER_SPEC)); 
            //generacion de llaves publico y privado
            kp=kpg.genKeyPair();
               
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(MainECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            System.getLogger(MainECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            
        ECCCipherService cipher=new ECCCipherService(kp); 
        
        String message="This is a message signed with ECC Service!!";
        String signedText=cipher.sign(message);
        boolean isVerified=cipher.verify(message,signedText);
            
        System.out.println("Mesange Original: "+message);
        System.out.println("Mensage firmado: "+signedText);
        System.out.println("Esta verificado: "+isVerified);
        
        
    }
    
    
    
}
