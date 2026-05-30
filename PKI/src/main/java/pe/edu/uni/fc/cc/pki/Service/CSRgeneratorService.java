/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.pki.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import static pe.edu.uni.fc.cc.common.Constants.BCFIPS_PROVIDER;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;
import static pe.edu.uni.fc.cc.common.Constants.RSA_SIGN_ALGORITHM;

/**
 *
 * @author Usuario
 */
public class CSRgeneratorService {
    public KeyPair generateKeyPair(){
        KeyPair kp=null;
        try {         
            KeyPairGenerator kpg=KeyPairGenerator.getInstance(RSA_ALGORITHM,BCFIPS_PROVIDER);
            kpg.initialize(RSA_KEY_SIZE_2048);
            kp=kpg.genKeyPair();
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(CSRgeneratorService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchProviderException ex) {
            System.getLogger(CSRgeneratorService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return kp;
    }
    
    public PKCS10CertificationRequest createCSR(KeyPair userKeyPair,String userDN){
        PKCS10CertificationRequest csr=null;
        X500Name subjectDN=new X500Name(userDN);
        JcaPKCS10CertificationRequestBuilder csrBuilder=new JcaPKCS10CertificationRequestBuilder(subjectDN, userKeyPair.getPublic());

        try {
            //declaracion del firmador
            ContentSigner userSigner =new JcaContentSignerBuilder(RSA_SIGN_ALGORITHM).setProvider(BCFIPS_PROVIDER).build(userKeyPair.getPrivate());
            csr=csrBuilder.build(userSigner);
            
            
        } catch (OperatorCreationException ex) {
            System.getLogger(CSRgeneratorService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return csr;
    }
}
