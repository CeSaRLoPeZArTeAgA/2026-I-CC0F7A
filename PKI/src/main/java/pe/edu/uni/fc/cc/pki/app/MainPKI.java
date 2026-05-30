/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.pki.app;

import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import static pe.edu.uni.fc.cc.common.Constants.CA_ROOT_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.CA_ROOT_CONTAINER_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.END_USER_ALIAS;
import static pe.edu.uni.fc.cc.common.Constants.END_USER_CONTAINER_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.KEY_USE_PASSWORD;
import pe.edu.uni.fc.cc.pki.Service.CSRgeneratorService;
import pe.edu.uni.fc.cc.pki.Service.CertificateSigningService;
import pe.edu.uni.fc.cc.pki.Service.CertificationAuthorityGenerationService;
import pe.edu.uni.fc.cc.pki.Service.KeyStoreStorageService;
import pe.edu.uni.fc.cc.pki.Service.KeyStoreStorageService.Credential;

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
        CSRgeneratorService csrService=new CSRgeneratorService();
        CertificateSigningService signService=new CertificateSigningService();
        
        //parametros de archivo o credenciales
        String caPKCS12Path=CA_ROOT_CONTAINER_FILENAME;
        String userPKCS12Path=END_USER_CONTAINER_FILENAME;
        String globalPassword=KEY_USE_PASSWORD;
        String endUser=END_USER_CONTAINER_FILENAME;//ojo
        
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
        
        //Generar el CSR del usuario
        KeyPair userKeyPair=csrService.generateKeyPair();
        String userDN="CN=Cesar Lopez, OU=Facultad de Ciencias, O=UNI, C=PE";
        PKCS10CertificationRequest userCsr=csrService.createCSR(userKeyPair,userDN);
        
        System.out.println("CSR del usuario ha sido generado!!");
        System.out.println("subjectDN: "+userCsr.getSubject());
        
        //cargara la CA desde el container
        Credential credential=storageService.loadKeyMaterialFromPKCS12File(caPKCS12Path, globalPassword, CA_ROOT_ALIAS);
        System.out.println("CA cargada edede el contenedor");
        
        //firmar el CSR del usuario final
        X509Certificate userCer=signService.signCsr(userCsr,credential,365);
        System.out.println("CD de usuario final generado!!");
        
        //almacenar llaves y CD del usuario final
        storageService.saveToPKCS12File(userPKCS12Path,globalPassword,END_USER_ALIAS, userKeyPair.getPrivate(), userCer);
        System.out.println("Certiifcado de usario final en: "+endUser);//ojo
                        
                
    }
}
