/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.hash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import static pe.edu.uni.fc.cc.common.Constants.PBKDF2_WITH_HMAC_SHA_256_ALGORITHM;
import pe.edu.uni.fc.cc.common.Utils;

/**
 *
 * @author Usuario
 */
public class HashingWithSalt {
    public static void main(String[] args) {
        System.out.println("Hashing With Salt!!!!");
        final String password="12345"; //baja entropia
        final String salt="user@example.com";//new SecrureRandom().nextbytes(salr); en la BD
        final int iterations=32;//numero de vueltas o iteraciones q se aplica la funcion internamente
        final int keysize=512;
        
        PBEKeySpec keySpec=new PBEKeySpec(password.toCharArray(),salt.getBytes(),iterations,keysize); 
        
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_WITH_HMAC_SHA_256_ALGORITHM);
            byte[] hashed = skf.generateSecret(keySpec).getEncoded();
            System.out.println("El valor SHA-256 con salt y con PBKDF es:"+Utils.byteToHex(hashed));
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(HashingWithSalt.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeySpecException ex) {
            System.getLogger(HashingWithSalt.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
