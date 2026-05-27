/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.pki;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import static pe.edu.uni.fc.cc.common.Constants.BCFIPS_PROVIDER;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;
import static pe.edu.uni.fc.cc.common.Constants.RSA_SIGN_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.USER_CD_FILENAME;

/**
 *
 * @author Usuario
 */
public class PKI {

    public static void main(String[] args) {
         System.out.println("USO DE PKI!");
    
         //agregando el proveedor criptografico
        Security.addProvider(new BouncyCastleFipsProvider());
        
        try {
            //generar par de llaves
            KeyPairGenerator kpg=KeyPairGenerator.getInstance(RSA_ALGORITHM);
            kpg.initialize(RSA_KEY_SIZE_2048);
            KeyPair userKeyPair=kpg.genKeyPair();
            
            //elaborar el DN del subject del usuario
            String user_dn="CN=Cesar Lopez, OU=Facultad de Ciencias, O=UNI, C=PE";
            X500Name subjectDN=new X500Name(user_dn);
            
            //generacion del CSR
            JcaPKCS10CertificationRequestBuilder csrBuilder= new JcaPKCS10CertificationRequestBuilder(subjectDN,userKeyPair.getPublic());
            
            //cosntruir un firmador---ojo aca
            ContentSigner userSigner= new JcaContentSignerBuilder(RSA_SIGN_ALGORITHM).build(userKeyPair.getPrivate());
            
            //construccion del CSR
            PKCS10CertificationRequest csr=csrBuilder.build(userSigner);
            
            //impression de la construccion correcta de CSR - comprobacion
            System.out.println("CSR generado: " + csr.getSubject().toString());
            
            //en la CA se recibe el CSR, se valida y se emite el CD
            //simular la CA 
            KeyPair caKeyPair= kpg.generateKeyPair();
            
            //el subjeto y emisor son iguales,en este caso, por lo que se autofirma
            String ca_dn="CN= UNI Root, O= UNI, C=PE";            
            X500Name issuerDN=new X500Name(ca_dn);
            
            //numero de serie simulado
            BigInteger serialNumber= BigInteger.valueOf(System.currentTimeMillis());
            
            //fecha de inicio de CA
            Date startDate =new Date();
            
            //tiempo de fin de CA
            Date endDate=new Date(System.currentTimeMillis()+(365L * 24 * 60 * 60 * 1000));
            
            //obteniendo la llave publicas del usuario desde el CSR 
            JcaPKCS10CertificationRequest jcaCsr = new JcaPKCS10CertificationRequest(csr).setProvider(BCFIPS_PROVIDER);
          
            //desde el Jca traeme la llave publica
            PublicKey userPublicKey =jcaCsr.getPublicKey();
            
            //construir el formato del CA X.509v3 para firmar el CS del usuario
            X509v3CertificateBuilder crtBuilder= new JcaX509v3CertificateBuilder(issuerDN,serialNumber,startDate,endDate,csr.getSubject(),userPublicKey);
          
            //firmar el CD utilizando la llave privada del a CA
            ContentSigner caSigner=  new JcaContentSignerBuilder(RSA_SIGN_ALGORITHM).build(caKeyPair.getPrivate());
           
            //guardamos el CD en: (fomato Castle)
            X509CertificateHolder crtHolder= crtBuilder.build(caSigner);
           
            //convertir al estandar JAVA
            X509Certificate userDC= new JcaX509CertificateConverter().setProvider(BCFIPS_PROVIDER).getCertificate(crtHolder);
           
            System.out.println("Issuer: "+ userDC.getIssuerX500Principal());
            System.out.println("Subject: "+ userDC.getSubjectX500Principal());
            
            //guardar  el CD en archivo
            try (FileOutputStream fos= new FileOutputStream(USER_CD_FILENAME)){
            fos.write(userDC.getEncoded());
                System.out.println("Archivo Almacenado"+USER_CD_FILENAME);
            } catch (IOException ex) {
                System.getLogger(PKI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(PKI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (OperatorCreationException ex) {
            System.getLogger(PKI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(PKI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (CertificateException ex) {
            System.getLogger(PKI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            
            
        } 
    }
}
