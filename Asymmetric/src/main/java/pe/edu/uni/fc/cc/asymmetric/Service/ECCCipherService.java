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
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import pe.edu.uni.fc.cc.asymmetric.ECCCipher;
import static pe.edu.uni.fc.cc.common.Constants.ECC_SIGN_ALGORITHM;

/**
 *
 * @author Usuario
 */
public class ECCCipherService {
    PublicKey publicKey;
    PrivateKey privateKey;
    public ECCCipherService(KeyPair keyPair){
        this.publicKey=keyPair.getPublic();
        this.privateKey=keyPair.getPrivate();
    }
    
    public String sign(String plainText){
        String result="";
        
        try {
            //objeto q ayuda a firmar, INSTACIANDO
            Signature signature=Signature.getInstance(ECC_SIGN_ALGORITHM);
            
            //inicializando la instancia
            signature.initSign(privateKey);
            
            //configurando el texto plano para ser firmado
            signature.update(plainText.getBytes());
            
            //ahora si se firma
            byte[] signed=signature.sign();
            
            //pasando a 
            result=Base64.getEncoder().encodeToString(signed);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (SignatureException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return result;
    }
    
    public boolean verify(String plainText, String signedText){
        boolean result=false;
        
        Signature signature;
        try {
            signature = Signature.getInstance(ECC_SIGN_ALGORITHM);
             //inicilaizacion la verificacion
            signature.initVerify(publicKey);
            //decodicar y luego verifica
            signature.update(plainText.getBytes());
        
            result=signature.verify(Base64.getDecoder().decode(signedText));
            
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (SignatureException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
           
        
        return result;
    }
}
