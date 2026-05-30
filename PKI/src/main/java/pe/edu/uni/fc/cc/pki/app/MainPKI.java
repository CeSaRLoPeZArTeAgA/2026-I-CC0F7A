/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.pki.app;

import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import static pe.edu.uni.fc.cc.common.Constants.CA_ROOT_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.CA_ROOT_CONTAINER_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.KEY_USE_PASSWORD;

import pe.edu.uni.fc.cc.pki.Service.CertificationAuthorityGenerationService;
import pe.edu.uni.fc.cc.pki.Service.KeyStoreStorageService;

/**
 *
 * @author Usuario
 */
public class MainPKI {
    public static void main(String[] args) {
        System.out.println("Main PKI!!!");
        
        //registrando al proveedor BC-FIPS
        Security.addProvider(new BouncyCastleFipsProvider());
        
        //instancias servicios
        CertificationAuthorityGenerationService caService =new CertificationAuthorityGenerationService();
        KeyStoreStorageService storageService =new KeyStoreStorageService();
        
        //parametros de archivo o credenciales
        String caPKCS12Path=CA_ROOT_CONTAINER_FILENAME;
        String globalPassword=KEY_USE_PASSWORD;
        
        //generar la CA autofirmada (ROOT)
        KeyPair caKeyPair=caService.generateKeyPair();
        
        //nombre de certificado (ROOT)
        String caDn="CN= ECERNEP ROOT CA, O=Estado Peruano, C=PE ";
        
        //creando cerificado raiz
        X509Certificate caCer=caService.createSelfSignedCertificate(caKeyPair, caDn, 10);
        
        //imprimiento el DN del certificado
        System.out.println("SubjectDN: "+caCer.getSubjectX500Principal());
        
        //alamacenar la CA
        storageService.saveToPKCS12File(caPKCS12Path, globalPassword, CA_ROOT_ALIAS,caKeyPair.getPrivate(), caCer);
        System.out.println("Contenedor de la CA ROOT almacenado en: "+caPKCS12Path);
        
        
    }
}
