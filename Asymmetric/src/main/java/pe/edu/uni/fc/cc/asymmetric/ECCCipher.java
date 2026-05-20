/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.asymmetric;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import static pe.edu.uni.fc.cc.common.Constants.ECC_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.ECC_KEY_PARAMETER_SPEC;
import static pe.edu.uni.fc.cc.common.Constants.ECC_SIGN_ALGORITHM;


/**
 *
 * @author Usuario
 */
public class ECCCipher {
    public static void main(String[] args) throws InvalidAlgorithmParameterException {
        System.out.println("ECCCipher!!");
        
        try {
            //inicializacion del generador del algortimo
            KeyPairGenerator kpg=KeyPairGenerator.getInstance(ECC_ALGORITHM);
            
            //configuracion de los parametros del algoritmo
            kpg.initialize(new ECGenParameterSpec(ECC_KEY_PARAMETER_SPEC));
            
            //generacion de llaves publico y privado
            KeyPair kp=kpg.genKeyPair();
            PublicKey publicKey=kp.getPublic();
            PrivateKey privateKey=kp.getPrivate();
            
            //
            String message="This is a message signed with ECC!!";
            String signedText=sign(privateKey,message);
            boolean isVerified=verify(publicKey,signedText,message);
            
            System.out.println("Mesange Original: "+message);
            System.out.println("Mensage firmado: "+signedText);
            System.out.println("Esta verificado: "+isVerified);
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            System.getLogger(ECCCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private static String sign(PrivateKey priKey,String plainText){
        String result="";
        try {
            //objeto q ayuda a firmar, INSTACIANDO
            Signature signature=Signature.getInstance(ECC_SIGN_ALGORITHM);
            
            //inicializando la instancia
            signature.initSign(priKey);
            
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
    
    private static boolean verify(PublicKey pubKey, String signedText,String plainText){
        boolean result=false;
        
         Signature signature;
        try {
            signature = Signature.getInstance(ECC_SIGN_ALGORITHM);
             //inicilaizacion la verificacion
            signature.initVerify(pubKey);
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
