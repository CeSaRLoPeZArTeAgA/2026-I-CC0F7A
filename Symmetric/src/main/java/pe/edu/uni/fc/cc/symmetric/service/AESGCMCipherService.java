/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.service;


import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_IV_LENGTH;
import static pe.edu.uni.fc.cc.common.Constants.TAG_LENGTH;
import static pe.edu.uni.fc.cc.common.Constants.TRANSFOMATION_AES_GCM;

/**
 *
 * @author Usuario
 */
public class AESGCMCipherService {
    
    //contenedor de la llave, en una Arreglo de bytes que almacena la llave secreta AES.
    //para AES, la llave puede tener longitudes, 16 bytes = 128 bits,
    //24 bytes = 192 bits, 32 bytes = 256 bits
    private final byte[] key;
    
    //constructor de la clase, recibe una llave en forma de arreglo de bytes y la guarda en el atributo privado key
    public AESGCMCipherService(byte[] key){
        this.key=key;
    }
    
    //metodo de encriptacion de texto, recibe como parametros el texto plano, vector de 
    //inicializacion(12 bytes) y aad datos autenticados adicionales
    // El aad no se cifra, pero sí se autentica. Eso significa que si cambia el AAD durante el descifrado, AES-GCM detectará el error y fallará.
    public byte[] encryptString(byte[] plainText, byte[] iv, byte[] aad){
        //variable donde se almacenará el resultado final cifrado en Base64.
        byte[] result=null;
        
        try {
            //e crea una instancia del cifrador usando la transformación AES-GCM
            Cipher cipher=Cipher.getInstance(TRANSFOMATION_AES_GCM);
            
            //Se construye la llave AES a partir del arreglo de bytes key
            SecretKeySpec keySpec=new SecretKeySpec(key,AES_ALGORITHM);
            
            //Se definen los parámetros de AES-GCM. TAG_LENGTH es la longitud del tag de autenticación en bits. iv es el vector de inicialización.
            GCMParameterSpec paramspec= new GCMParameterSpec(TAG_LENGTH,iv);
            
            //Se inicializa el Cipher en modo cifrado. Cipher.ENCRYPT_MODE indica que se va a cifrar. keySpec es la llave AES. paramspec contiene el TAG_LENGTH y el IV.
            cipher.init(Cipher.ENCRYPT_MODE, keySpec,paramspec);
            
            // Si aad no es null, se agrega como dato autenticado adicional.
            // El AAD debe ser exactamente el mismo en cifrado y descifrado.
            // Si cambia, el descifrado falla.
            if(aad!=null){
                cipher.updateAAD(aad);
            }
            
            // Se convierte el texto plano a bytes usando UTF-8 y luego se cifra con doFinal().
            // En AES-GCM, el resultado contiene: ciphertext || tag, es decir texto cifrado más tag de autenticación.
            //byte[] encrypted=cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] encrypted=cipher.doFinal(plainText);
            
            
            // Se crea un nuevo arreglo donde se guardará: IV || ciphertext || tag
            // Esto es útil porque el IV se necesita para descifrar.
            // El IV no es secreto, por eso puede guardarse junto al texto cifrado.
            byte[] ciphered=new byte[iv.length+encrypted.length];
            
            /* Copia el IV al inicio del arreglo ciphered.
            Desde iv, posición 0,
            hacia ciphered, posición 0,
            copiando iv.length bytes.*/
            System.arraycopy(iv,0,ciphered,0,iv.length);
            
            /* Copia el texto cifrado después del IV.
            Desde encrypted, posición 0,
            hacia ciphered, posición iv.length,
            copiando encrypted.length bytes. */
            System.arraycopy(encrypted, 0,ciphered,iv.length,encrypted.length);
            
            /* Se codifica el arreglo final en Base64.
            Esto permite representar bytes binarios como texto,
            para imprimirlos, guardarlos o enviarlos. */
            //result=Base64.getEncoder().encodeToString(ciphered);
            result=ciphered;
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        //salida de texto cifrado codificado en Base64
        return result;
    }
    
    public String encryptString(String plainText,byte[] iv,byte[] add){
        byte[] ciphered=encryptString(plainText.getBytes(StandardCharsets.UTF_8),iv,add);
        return Base64.getEncoder().encodeToString(ciphered);
    }
    
    //metodo de desencriptacion
    public byte[] decrypt(byte[] encryptedText,byte[] aad){
        // variable donde se almacenara el texto descifrado
        //String result="";
        byte[] result=null;
        byte[] input=encryptedText;
        
        // Se decodifica el texto Base64.
        // encryptedText es texto legible, pero internamente representa bytes.
        //byte[] input=Base64.getDecoder().decode(encryptedText);
        
        // Se crea un arreglo para guardar el IV extraído. En AES-GCM normalmente el IV tiene 12 bytes.
        byte[] iv=new byte[AES_GCM_IV_LENGTH];
        
        // Se crea un arreglo para guardar: ciphertext || tag
        // Como input tiene: IV || ciphertext || tag, entonces se resta la longitud del IV.
        byte[] cipherText=new byte[input.length-AES_GCM_IV_LENGTH];

        // Extrae el IV desde input.
        // Copia los primeros AES_GCM_IV_LENGTH bytes de input hacia el arreglo iv.
        // Es decir: iv = input[0 ... AES_GCM_IV_LENGTH - 1]
        System.arraycopy(input, 0, iv, 0, AES_GCM_IV_LENGTH);

        // Extraer texto cifrado + tag GCM
        // Extrae el texto cifrado más el tag de autenticación.
        // Copia desde input[AES_GCM_IV_LENGTH] hasta el final.
        // Es decir:
        // cipherText = input[AES_GCM_IV_LENGTH ... input.length - 1]
        System.arraycopy(input, AES_GCM_IV_LENGTH, cipherText, 0, cipherText.length);
        
        try {
            // Se crea una instancia del Cipher usando AES-GCM.
            Cipher cipher=Cipher.getInstance(TRANSFOMATION_AES_GCM);
            
            //Se reconstruye la llave AES a partir del arreglo de bytes key
            SecretKeySpec keySpec=new SecretKeySpec(key,AES_ALGORITHM);
            
            // Se reconstruyen los parámetros de AES-GCM usando:
            // - TAG_LENGTH
            // - IV extraído del mensaje cifrado
            GCMParameterSpec paramSpec=new GCMParameterSpec(TAG_LENGTH,iv);
            
            // Se inicializa el Cipher en modo descifrado.
            // Cipher.DECRYPT_MODE indica que se va a descifrar.
            cipher.init(Cipher.DECRYPT_MODE,keySpec,paramSpec);
            
            // Si existe AAD, se debe agregar antes de llamar a doFinal().
            //Importante: el AAD debe coincidir exactamente con el usado en encryptString().
            if(aad!=null){
                cipher.updateAAD(aad);
            }
            
            // Se descifra el texto cifrado.
            // Además, AES-GCM verifica automáticamente el tag de autenticación.
            // Si algo fue alterado, doFinal() lanzará una excepción.
            byte[] decrypted=cipher.doFinal(cipherText);
            
            //Se convierte el arreglo de bytes descifrado a String usando UTF-8.  
            //result=new String(decrypted,StandardCharsets.UTF_8);  
            result=decrypted;  
    

        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(AESGCMCipherService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }  
        return result;
    }
    
    public String decrypt(String encrypedText,byte[] add){    
        byte[] input=Base64.getDecoder().decode(encrypedText);
        byte[] decryted=decrypt(input,add);
        String result=new String(decryted,StandardCharsets.UTF_8);
        return result;
    }
}
