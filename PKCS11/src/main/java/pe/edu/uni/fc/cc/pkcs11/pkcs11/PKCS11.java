/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.pkcs11.pkcs11;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import static pe.edu.uni.fc.cc.common.Constants.PKCS11_KEYSTORE_TYPE;
import static pe.edu.uni.fc.cc.common.Constants.SUNPKCS11_PROVIDER;

/**
 *
 * @author Usuario
 */
public class PKCS11 {

    public static void main(String[] args) {
        System.out.println("PKCS#11!");
        //Datos para establecer la secion
        //datos de activacion(tambien es la credencial del contenedor)
        String pin="12eA5678";
        
        //dato para configurar el API(motor criptografico)
        String[] dllPaths={
            "c:\\Windows\\System32\\eToken.dll",
            "c:\\Windows\\System32\\cvP11.dll",
            "c:\\Windows\\System32\\crytoide_pkcs11.dll",
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
                "library=" + dllPaths[0] + "\n"+
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
        /*
        Security.addProvider(provider);
        System.out.println("Provider con nueva configuracion cargado satifatoriamente!!");
        try {
            //tratando con el material sencible
            KeyStore ks=KeyStore.getInstance(PKCS11_KEYSTORE_TYPE,provider);
        } catch (KeyStoreException ex) {
            System.getLogger(PKCS11.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        */
    }
}
