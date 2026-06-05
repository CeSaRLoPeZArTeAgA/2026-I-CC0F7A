/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.digitalsignature;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;

/**
 *
 * @author Usuario
 */
public class BasicDigitalSignature {

    public static void main(String[] args) {
        System.out.println("Basic Digita Signature!!");
        //mensaje a firmar y enviar
        String originalMessage="Basic Digital Signature";
       
        //llaves RSA
        KeyPair kp=null;
        try {
            KeyPairGenerator kpg=KeyPairGenerator.getInstance(RSA_ALGORITHM);
            kpg.initialize(RSA_KEY_SIZE_2048);
            kp=kpg.genKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(BasicDigitalSignature.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        //par de llaves
        PrivateKey privateKey=kp.getPrivate();
        PublicKey publicKey=kp.getPublic();
        
        //visualizacio los componentes de las llaves
        RSAPrivateKey rsaPrivateKey=(RSAPrivateKey) privateKey;//(d,n)
        RSAPublicKey rsaPublicKey=(RSAPublicKey) publicKey;//(e,n)
        
        //extraccion de las componentes o parametros de la llave privada
        BigInteger d=rsaPrivateKey.getPrivateExponent();
        BigInteger n=rsaPrivateKey.getModulus();
        
        //para pasar el string del mensaje a representacion numerica
        BigInteger originalBigIntegerMessage=new BigInteger(1,originalMessage.getBytes());
        
        //firmar el mensaje
        BigInteger signature=originalBigIntegerMessage.modPow(d, n);//s=x^d mod n
        
        //resultados impresion
        System.out.println("Mensaje original: "+originalMessage);
        System.out.println("Mensaje numerico: "+originalBigIntegerMessage);
        System.out.println("Mensaje firmado: "+ signature);
        System.out.println("Modulo (n):" +n);
        System.out.println("Modulo (d):" +d);
        
        //-----verificacion
        //componentes de la llavae publica
        BigInteger e=rsaPublicKey.getPublicExponent();
        
        //desciiframos con la llave publica(verificacion)
        BigInteger recoveredMessage=signature.modPow(e, n);//x=s^e mod n
        
        //comparacion
        boolean verified =recoveredMessage.equals(originalBigIntegerMessage);
        
        //impresion de salida de verificacion
        System.out.println("Mensaje recuperado: "+recoveredMessage);
        System.out.println("Firma Valida: "+ verified);
        
        
    }
}
