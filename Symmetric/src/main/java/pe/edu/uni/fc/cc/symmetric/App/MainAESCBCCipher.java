/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.App;

import java.security.SecureRandom;
import static pe.edu.uni.fc.cc.common.Constants.AES_CBC_IV_LENGTH;
import pe.edu.uni.fc.cc.common.Utils;
import static pe.edu.uni.fc.cc.symmetric.AESCBCCipher.decrypt;
import static pe.edu.uni.fc.cc.symmetric.AESCBCCipher.encrypt;
import pe.edu.uni.fc.cc.symmetric.service.AESCBCCipherService;

/**
 *
 * @author Usuario
 */
public class MainAESCBCCipher {
    public static void main(String[] args) {
        System.out.println("Main Symmetric AES CBC!!");
        
        //fuente de seudo aleatoridad
        SecureRandom sr=new SecureRandom();
        
        //contenedor de la llave AES
        byte[] key=new byte[16];//128 bits -> 16 byte=128/8
        //generacion de la llave AES, necesita cadena aleatoria del objeto sr
        sr.nextBytes(key);
        //visualizacion de llave en hexadecimal
        System.out.println("key: "+Utils.byteToHex(key));
        
        //contenedor del vector de inicializacion IV (Inition Vector), tiene que tener la longitud de la llave
        //byte[] initVector=new byte[16];
        //generacion del vector de inicializacion IV
        //sr.nextBytes(initVector);
        //generacion del vector de inicializacion con programa de Utils
        byte[] initVector=Utils.generateIV(AES_CBC_IV_LENGTH);
        //visualizacion del vector de inicializacion
        System.out.println("Vector Inicializacion: "+Utils.byteToHex(initVector));
            
        AESCBCCipherService cipher=new AESCBCCipherService(key);
        //texto plano a cifrar
        String payload="This is a plaintext sent from ALice to Bod.";
        
        //texto encriptado
        String encrypted=cipher.encrypt(initVector,payload);
        
        //texto desencriptado
        String decrypted=cipher.decrypt(encrypted);
        
        //impresion de los texto original, encriptado y desencriptado
        System.out.println("Texto Original: "+payload);
        System.out.println("Texto cifrado: "+encrypted);
        System.out.println("Texto decifrado: "+decrypted);
        
        //compara la cadena original con la cadena desencriptada. Si sale bien es OK, si sale mal KO
        String result=decrypted.equals(payload)?"OK":"KO!";
        //impresion de la comparacion de las cadenas
        System.out.println("Iguales? "+result);     
    }
}
