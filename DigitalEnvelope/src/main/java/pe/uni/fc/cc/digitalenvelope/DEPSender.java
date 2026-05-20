/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.uni.fc.cc.digitalenvelope;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import pe.edu.uni.fc.cc.asymmetric.Service.RSACipherService;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_ADD;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_IV_LENGTH;
import static pe.edu.uni.fc.cc.common.Constants.AES_KEY_SIZE_256;
import pe.edu.uni.fc.cc.common.Utils;
import pe.edu.uni.fc.cc.symmetric.service.AESGCMCipherService;

/**
 *
 * @author Usuario
 */
public class DEPSender {
    //vecto de inicializacion
    //private byte[] iv;
    
    //mensaje cifrado
    private byte[] cipheredMessage;
    
    //drapeo de la llave
    private byte[] digitalEnvelope;

    /*public byte[] getIv() {
        return iv;
    }*/

    public byte[] getCipheredMessage() {
        return cipheredMessage;
    }

    public byte[] getDigitalEnvelope() {
        return digitalEnvelope;
    }
    
    public void prepareForShipping(String message, PublicKey pubKey){
        
      
        try {
            //generar llave simetrica
            KeyGenerator kg = KeyGenerator.getInstance(AES_ALGORITHM);
            kg.init(AES_KEY_SIZE_256);
            SecretKey aesKey=kg.generateKey();
            //cifrar el mensaje con la llave AES_GCM
            AESGCMCipherService aes_gcm_cipher=new AESGCMCipherService(aesKey.getEncoded());
            //parametro del cifrado AES-GCM
            byte[] iv=Utils.generateIV(AES_GCM_IV_LENGTH);
            cipheredMessage=aes_gcm_cipher.encryptString(message.getBytes(), iv,AES_GCM_ADD);
            //crear el sobre digital
            RSACipherService rsa_cipher=new RSACipherService(pubKey);
            digitalEnvelope=rsa_cipher.encrypt(aesKey.getEncoded());
            
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(DEPSender.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
    }
}
