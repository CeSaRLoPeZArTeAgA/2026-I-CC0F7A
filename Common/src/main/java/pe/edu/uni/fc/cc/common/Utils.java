/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;
import javax.crypto.SecretKey;
import static pe.edu.uni.fc.cc.common.Constants.SHA_256_ALGORITHM;

/**
 * 
 * @author Usuario
 */
public class Utils {
    //Para el calculo de inverso modular de a mod module
    public static int getInverseModule(int a,int module){
        int inv_a=0;
        int tmp;
        for (int i = 0; i < module; i++) {
            tmp=(a*i)% module;
            if (tmp==1) {
                inv_a=i;
            }
        }
        return inv_a;
    } 
    
    //calculo de maximo comun divisor (Algoritmo de Euclides)
    public static int gcd(int x, int y){
        while(y !=0){
          int tmp=y;
          y=x%y;
          x=tmp;
        }
        return x;
    }
    
    //funcion que pasa de una cadena de bytes a hexadecimal
    public static String byteToHex(byte[] bytes){
        return HexFormat.of().withUpperCase().withDelimiter(" ").formatHex(bytes);
    }
    
    //funcion de generacion de vector de inicializacion
    public static byte[] generateIV(int length){
        //contenedor del vector de inicializacion IV (Inition Vector), tiene que tener la longitud de la llave
        byte[] initVector=new byte[length];
        
        //fuente de seudo aleatoridad
        SecureRandom sr=new SecureRandom();
        
        //generacion del vector de inicializacion IV
        sr.nextBytes(initVector);
        
        //retorno de la generacion del vector de inicializacion
        return initVector;
    }
    
    public static String getKeyHash(SecretKey sk){
        byte[] keyBytes=sk.getEncoded();
        byte[] hash=null;
        try {
            
            MessageDigest md=MessageDigest.getInstance(SHA_256_ALGORITHM);
            hash=md.digest(keyBytes);
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(Utils.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return byteToHex(hash);
    } 
}
