/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.pkcs11.pkcs11;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static pe.edu.uni.fc.cc.common.Constants.PKCS11_KEYSTORE_TYPE;
import static pe.edu.uni.fc.cc.common.Constants.RSA_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.RSA_KEY_SIZE_2048;
import static pe.edu.uni.fc.cc.common.Constants.RSA_SIGN_ALGORITHM;
import static pe.edu.uni.fc.cc.common.Constants.SUNPKCS11_PROVIDER;
import static pe.edu.uni.fc.cc.common.Constants.TRANSFOMATION_RSA;

/**
 *
 * @author Usuario
 */
public class PKCS11 {

    public static void main(String[] args) {
        System.out.println("PKCS#11!");
        
        //mensaje a cifrar
        String original_message="Mensaje PKCS#11";
        System.out.println("Mensaje original: "+original_message);
        
        //Datos para establecer la secion
        //datos de activacion(tambien es la credencial del contenedor)
        String pin="12eA5678";
        
        //dato para configurar el API(motor criptografico)
        String[] dllPaths={
            "c:\\Windows\\System32\\eToken.dll",
            "c:\\Windows\\System32\\cvP11.dll",
            "c:\\Windows\\System32\\cryptoide_pkcs11.dll",
            "c:\\Windows\\System32\\bit4xpki.dll",
            "c:\\Windows\\System32\\eps2003csp11.dll",
        };
        
        //slots
        String[] slots={
            "0",
            "1",
            "2",
            "3", //safenet
        };
        
        //configuracion y atributos
        String pkcs11config=
                "--name=Token" + "\n"+
                "library=" + dllPaths[2] + "\n"+
                "slotListIndex=" + slots[0] + "\n"+
                "attributes(*,CKO_PRIVATE_KEY,*) = {" + "\n"+
                "   CKA_TOKEN=true"+"\n"+
                "   CKA_SENSITIVE=true"+"\n"+
                "   CKA_EXTRACTABLE=false"+"\n"+
                "   CKA_SIGN=true"+"\n"+
                "   CKA_DECRYPT=true"+"\n"+
                "}"+"\n"+
                "attributes(*, CKO_PUBLIC_KEY,*) = {" + "\n"+
                "   CKA_VERIFY=true" + "\n" +
                "   CKA_ENCRYPT=true" + "\n"+
                "}" + "\n";
        
                
        System.out.println("Parametros establecidos!!");
        System.out.println("Security providers: "+Arrays.toString(Security.getProviders()));
        
        //establecer provider
        Provider provider=Security.getProvider(SUNPKCS11_PROVIDER);
        if(provider==null) return;
        System.out.println("Provider cargado con configuracion por defecto, cargado satisfatoriamente!!!!");
        
        //cargar la configuracion
        provider=provider.configure(pkcs11config);//falta la instalacion de las .dll del provider
        
        Security.addProvider(provider);
        System.out.println("Provider con nueva configuracion cargado satifatoriamente!!");
        try {
            //tratando con el material sencible
            KeyStore ks=KeyStore.getInstance(PKCS11_KEYSTORE_TYPE,provider);
            System.out.println("Ya estamos dentro del token criptografico mToken CryptoID-E !!!");
            
            //iniciando seccion 
            ks.load(null, pin.toCharArray());
            System.out.println("Login exitoso");
            
            //revisando contenido del token
            System.out.println("Entries: " +ks.size());
            
            //tratando de leer un entries - listando
            Enumeration<String> aliases=ks.aliases();
            while(aliases.hasMoreElements()){
                String alias=aliases.nextElement();
                System.out.println("Alias encontrado: "+alias);
            }
            //generar llaves en le token
            System.out.println("Genrando llaves RSA");
            KeyPairGenerator kpg=KeyPairGenerator.getInstance(RSA_ALGORITHM, provider);
            
            //inicializando 
            kpg.initialize(RSA_KEY_SIZE_2048);
            
            //generando el par de llaves
            KeyPair kp=kpg.genKeyPair();
            System.out.println("Par de llaves RSA generadas exitosamente!!");
            System.out.println("Algortimo: "+kp.getPublic().getAlgorithm());
            System.out.println("Formato de la llave publica: "+kp.getPublic().getFormat());
            System.out.println("Llave privada: "+kp.getPrivate().toString());
            
            //cifrado RSA
            Cipher cipher_enc=Cipher.getInstance(TRANSFOMATION_RSA);
            cipher_enc.init(Cipher.ENCRYPT_MODE, kp.getPublic());
            byte[] encrytedBytes=cipher_enc.doFinal(original_message.getBytes(StandardCharsets.UTF_8));
            System.out.println("El mensaje cifrado(base 64): "+Base64.getEncoder().encodeToString(encrytedBytes));

            //descifrar mensaje con la llave privada en el token
            Cipher cipher_dec=Cipher.getInstance(TRANSFOMATION_RSA, provider);
            cipher_dec.init(Cipher.DECRYPT_MODE, kp.getPrivate());
            byte[] decryptedBytes=cipher_dec.doFinal(encrytedBytes);
            String recovered_message=new String(decryptedBytes,StandardCharsets.UTF_8);
            System.out.println("Mensaje recuperado: "+ recovered_message);
            
            //firma digital y verificacion
            System.out.println("Operacion de firma digital");
            //firmador
            Signature signer=Signature.getInstance(RSA_SIGN_ALGORITHM, provider);
            signer.initSign(kp.getPrivate());
            signer.update(original_message.getBytes(StandardCharsets.UTF_8));
            byte[] signedMessage=signer.sign();
            System.out.println("Firma generada(base 64): "+ Base64.getEncoder().encodeToString(signedMessage));
            //verificador de la firma
            System.out.println("Verificando la firma");
            Signature verifier=Signature.getInstance(RSA_SIGN_ALGORITHM);
            verifier.initVerify(kp.getPublic());
            //pasar el mensaje original
            verifier.update(original_message.getBytes(StandardCharsets.UTF_8));
            boolean verified=verifier.verify(signedMessage);
            System.out.println("Firma valida: "+verified);
        } catch (KeyStoreException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (CertificateException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchPaddingException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (InvalidKeyException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IllegalBlockSizeException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (BadPaddingException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (SignatureException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        
        
    }
}
