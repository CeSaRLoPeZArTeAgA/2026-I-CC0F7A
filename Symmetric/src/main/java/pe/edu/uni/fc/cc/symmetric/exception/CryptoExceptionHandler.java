/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.exception;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Usuario
 */
public class CryptoExceptionHandler {
    public static RuntimeException handle(Exception e){
        if(e instanceof NoSuchAlgorithmException || e instanceof  NoSuchPaddingException ){
            return new CryptoConfigurationException("Error de cpnfiguracion criptografica",e);
        }
        if(e instanceof  InvalidKeyException   || e instanceof  InvalidAlgorithmParameterException ){
            return new CryptoKeyException("Error en la llave o parametro",e);
        }
        
        if(e instanceof  IllegalBlockSizeException   || e instanceof   BadPaddingException ){
            return new CryptoOperationException("Error de tamaño de padding",e);
        }
        
         return new CryptoException("Error Crptografico",e);

    }    
}
