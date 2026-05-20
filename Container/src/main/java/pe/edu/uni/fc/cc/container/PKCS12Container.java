/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.container;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.AES_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.AES_KEY_SIZE_256;
import static pe.edu.uni.fc.cc.common.Constants.CONTAINER_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.CONTAINER_PASSWORD;
import static pe.edu.uni.fc.cc.common.Constants.KEY_USE_PASSWORD;
import static pe.edu.uni.fc.cc.common.Constants.PKCS12_KEYSTORE_TYPE;


/**
 *
 * @author Usuario
 */
public class PKCS12Container {

    public static void main(String[] args) {
        System.out.println("PKCS12 Container !!!");
        
        try {
            //generar llave AES
            KeyGenerator keyGen= KeyGenerator.getInstance(AES_ALGORITHM);
            
            //longitud de la llave
            keyGen.init(AES_KEY_SIZE_256);
            
            //guarda la llave en secretKeyOriginal en memoria
            SecretKey secretKeyOriginal=keyGen.generateKey();
            
            //crear el contenedor
            KeyStore ks=KeyStore.getInstance(PKCS12_KEYSTORE_TYPE);
            
            //datos q se mete en el contenedor, inicializamos con null en memoria 
            ks.load(null,CONTAINER_PASSWORD.toCharArray());
            
            //preparando la llave para su almacenamiento
            KeyStore.SecretKeyEntry entry=new KeyStore.SecretKeyEntry(secretKeyOriginal);
            
            //generar la proteccion de la llave
            KeyStore.PasswordProtection protection=new KeyStore.PasswordProtection(KEY_USE_PASSWORD.toCharArray());
            
            //guardando la llave con un id, en el almacen
            ks.setEntry(AES_ALIAS, entry, protection);
            
            //guardar el contenedor en archivo
            FileOutputStream fos=new FileOutputStream(CONTAINER_FILENAME);
            
            //guarda el flujo de salida
            ks.store(fos,CONTAINER_PASSWORD.toCharArray());
            
            System.out.println("done!!");
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(PKCS12Container.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (KeyStoreException ex) {
            System.getLogger(PKCS12Container.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(PKCS12Container.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (CertificateException ex) {
            System.getLogger(PKCS12Container.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
