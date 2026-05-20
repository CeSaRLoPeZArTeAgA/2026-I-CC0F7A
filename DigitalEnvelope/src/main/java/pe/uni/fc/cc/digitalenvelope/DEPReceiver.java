/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.uni.fc.cc.digitalenvelope;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import pe.edu.uni.fc.cc.asymmetric.Service.RSACipherService;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_ADD;
import pe.edu.uni.fc.cc.symmetric.service.AESGCMCipherService;

/**
 *
 * @author Usuario
 */
public class DEPReceiver {
    public String processShippedPayload(byte[] cipheredMessage, byte[] digitalEnvelope,PrivateKey priKey){
        //abriendo el sobre digital
        RSACipherService rsa_cipher=new RSACipherService(priKey);
        byte[] rawAesKey =rsa_cipher.decrypt(digitalEnvelope);
        SecretKey aeskey=new SecretKeySpec(rawAesKey, AES_ALGORITHM);
        //descifrar mensaje
        AESGCMCipherService aes_gcm_cipher=new AESGCMCipherService(aeskey.getEncoded());
        byte[] decryptedMessage=aes_gcm_cipher.decrypt(cipheredMessage, AES_GCM_ADD);
        String result=new String(decryptedMessage,StandardCharsets.UTF_8);
        System.out.println("Recived: mensaje cifrado y sobre digital");
        return result;
    }
}
