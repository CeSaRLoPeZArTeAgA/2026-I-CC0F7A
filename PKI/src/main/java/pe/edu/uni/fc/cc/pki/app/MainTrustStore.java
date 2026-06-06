/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.pki.app;

import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import static pe.edu.uni.fc.cc.common.Constants.CLIENT_TLS_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.CLIENT_TLS_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.KEY_USE_PASSWORD;
import static pe.edu.uni.fc.cc.common.Constants.SERVER_TLS_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.SERVER_TLS_FILENAME;
import pe.edu.uni.fc.cc.pki.Service.CertificationAuthorityGenerationService;
import pe.edu.uni.fc.cc.pki.Service.KeyStoreStorageService;


/**
 *
 * @author Usuario
 */
public class MainTrustStore {
    public static void main(String[] args) {
        System.out.println("Main Trust Store!!!");
        
        //registrar el proveedor BCFIPS
        Security.addProvider(new BouncyCastleFipsProvider());
        
        //instaciamos los servcios
        CertificationAuthorityGenerationService caService= new CertificationAuthorityGenerationService();
        KeyStoreStorageService storageService=new KeyStoreStorageService();
        
        //parametros de archivo y credenciales
        String serverTLsPKCS12Path=SERVER_TLS_FILENAME;
        String clientTLsPKCS12Path= CLIENT_TLS_FILENAME;
        String globalPassword=KEY_USE_PASSWORD;
        
        //generar CD AUTOFIRMADO
        KeyPair keyPair=caService.generateKeyPair();
        
        //autofirmado
        String dn="CN=localhost, o=UNI, OU=FC, C=PE";
        X509Certificate cert=caService.createSelfSignedCertificate(keyPair,dn,1);
        System.out.println("Container TLS Server Genrado");
        
        //almacenar el CD autofirmado
        storageService.saveToPKCS12File(serverTLsPKCS12Path,globalPassword,SERVER_TLS_ALIAS,keyPair.getPrivate(),cert);
        System.out.println("Container TLS server almacenado en: "+serverTLsPKCS12Path);
        
        //almacenar el Trusstore
        storageService.saveToTrustStore(clientTLsPKCS12Path,globalPassword,CLIENT_TLS_ALIAS,cert);
        System.out.println("Container TLS client almacenado en: "+clientTLsPKCS12Path);
    }
}
