/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.digitalsignature;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;
import static pe.edu.uni.fc.cc.common.Constants.SHA_256_ALGORITHM;

/**
 *
 * @author Usuario
 */
public class ProtocolDigitalSignature {
    public static void main(String[] args) {
        System.out.println("Protocol Digital Signute !!!");
        
         //mensaje a firmar y enviar
        String originalMessage="Protocol Digital Signature";
       
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
        BigInteger originalBigIntegerDigest=null;
        
        //calculo de hash - obtencion del resumen
        MessageDigest mdMachine=null;//motor de generacion de hash
        try {
            mdMachine=MessageDigest.getInstance(SHA_256_ALGORITHM);//instanciando
            byte[] originaldigest=mdMachine.digest(originalMessage.getBytes());
            originalBigIntegerDigest=new BigInteger(1,originaldigest);
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(ProtocolDigitalSignature.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        if(originalBigIntegerDigest==null) return;
        
        //firmar el resumen
        BigInteger signature=originalBigIntegerDigest.modPow(d, n);
        System.out.println("Firma del resumen del mensaje: "+signature);
        
        //se envia el mensaje y la firma. El emisor envia al receptor el mensaje y la firma
        //proceso de verificacion
        
        //parametro (e) desde la llave publica
        BigInteger e=rsaPublicKey.getPublicExponent();
        
        //recuperar el hash firmado
        BigInteger recoveredBigIntegerDigest=signature.modPow(e, n);
        
        //calcular el nuevo hash. El receptor calcula el hash del mensaje
        byte[] newDigest=mdMachine.digest(originalMessage.getBytes());
        
        //hash recuperado
        BigInteger newBigIntegerDigest=new BigInteger(1, newDigest);
        
        //comparar
        boolean verified=recoveredBigIntegerDigest.equals(newBigIntegerDigest);
        //imprimir
        System.out.println("Resumen recuperado: "+recoveredBigIntegerDigest.toString(16));//presentacion hexadecimal
        System.out.println("Resumen calculado: "+newBigIntegerDigest.toString(16));//presentacion hexadecimal
        System.out.println("Firma valida: " +verified);
        
    }
}
