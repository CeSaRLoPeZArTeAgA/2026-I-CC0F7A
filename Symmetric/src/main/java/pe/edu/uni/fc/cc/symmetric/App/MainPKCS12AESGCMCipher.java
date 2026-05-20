/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.symmetric.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.SecretKey;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.AES_GCM_IV_LENGTH;
import static pe.edu.uni.fc.cc.common.Constants.CONTAINER_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.CONTAINER_PASSWORD;
import static pe.edu.uni.fc.cc.common.Constants.KEY_USE_PASSWORD;
import static pe.edu.uni.fc.cc.common.Constants.PKCS12_KEYSTORE_TYPE;
import pe.edu.uni.fc.cc.common.Utils;
import pe.edu.uni.fc.cc.symmetric.service.AESGCMCipherService;

/**
 *
 * @author Usuario
 */
public class MainPKCS12AESGCMCipher {
    public static void main(String[] args) {
        System.out.println("Main PKCS12 AESGCM Cipher!!!");
        byte[] aesKeyBytes=null;
        try {
            //cargar el contenedor
            KeyStore ks=KeyStore.getInstance(PKCS12_KEYSTORE_TYPE);
            
            //cargar en memoria el contenedor
            FileInputStream fis=new FileInputStream(CONTAINER_FILENAME); 
            
            //abrir el contenedor
            ks.load(fis,CONTAINER_PASSWORD.toCharArray());
            //configurando la llamada a la caja fuerte SecretKeyEntry
            KeyStore.PasswordProtection protection=new KeyStore.PasswordProtection(KEY_USE_PASSWORD.toCharArray());
            KeyStore.SecretKeyEntry entry=(KeyStore.SecretKeyEntry)ks.getEntry(AES_ALIAS, protection);
            
            //verificacion q no traiga nada vacio
            if (entry==null) {
                System.out.println("No se encontro la llave con el alias");
                return;
            }
            //extraer la llave
            SecretKey sk=entry.getSecretKey();
            //String Utils;
            
            //obtener el hash de la llave
            System.out.println("hash de la llave AES:"+Utils.getKeyHash(sk) );
            
            //aca recien se tiene la llave en array de bytes
            aesKeyBytes=sk.getEncoded();
            
        } catch (KeyStoreException ex) {
            System.getLogger(MainPKCS12AESGCMCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (FileNotFoundException ex) {
            System.getLogger(MainPKCS12AESGCMCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(MainPKCS12AESGCMCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(MainPKCS12AESGCMCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (CertificateException ex) {
            System.getLogger(MainPKCS12AESGCMCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (UnrecoverableEntryException ex) {
            System.getLogger(MainPKCS12AESGCMCipher.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        //crea el objeto aes_gcm
        AESGCMCipherService aes_gcm=new AESGCMCipherService(aesKeyBytes);
        
        //generacion del vector inicial
        byte[] iv=Utils.generateIV(AES_GCM_IV_LENGTH);
        
        //dato de autenticacion, para verificar que el cifrado ha sido generado por el
        byte[] aad="Header".getBytes();
        
        //texto a cifrar
        String msg="This is message ciphered a AES GCM user key embebed into a PKCS12 container!!!";
        
        //encripta mensaje
        String encrypted=aes_gcm.encryptString(msg, iv, aad);
        
        //desencripta mensaje
        String decrypted=aes_gcm.decrypt(encrypted, aad);
        
        //impresion de resultados
        System.out.println("Original: "+msg);
        System.out.println("Encrypted: "+encrypted);
        System.out.println("Decryped: "+decrypted);
        
    }
   
}
