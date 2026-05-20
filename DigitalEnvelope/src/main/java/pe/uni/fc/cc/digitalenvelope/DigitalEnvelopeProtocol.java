/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.uni.fc.cc.digitalenvelope;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;
import pe.edu.uni.fc.cc.common.Utils;

/**
 *
 * @author Usuario
 */
public class DigitalEnvelopeProtocol {
    public static void main(String[] args) {
        System.out.println("DigitalEnvelopeProtocol!!");
        
        try {
            //las llaves RSA del receptot
            KeyPairGenerator kpg=KeyPairGenerator.getInstance(RSA_ALGORITHM);
            
            kpg.initialize(RSA_KEY_SIZE_2048);
            
            KeyPair kp=kpg.genKeyPair();
            //mensaje enviado
            String secret_message="Este es un mensahe confidencial enviado desde el emisor al receptor";
            DEPSender sender=new DEPSender();
            sender.prepareForShipping(secret_message, kp.getPublic());
            
            //mostra lo que viaja en la red
            System.out.println("Digital envelope: "+Utils.byteToHex(sender.getDigitalEnvelope()));
            System.out.println("Mensaje cifrado: "+Utils.byteToHex(sender.getCipheredMessage()));
            
            //el reciver procesa los datos recividos
            DEPReceiver receiver=new DEPReceiver();
            String recovered_message=receiver.processShippedPayload(sender.getCipheredMessage(),sender.getDigitalEnvelope(), kp.getPrivate());
            //mostramos el mensaje original y el recuparado
            System.out.println("Mensaje secreto: "+secret_message);
            System.out.println("Mensaje recuperado: "+recovered_message);
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(DigitalEnvelopeProtocol.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
