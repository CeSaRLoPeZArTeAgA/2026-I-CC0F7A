/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.common;

import java.nio.file.Paths;

/**
 *
 * @author Usuario
 */
public class Constants {
     public static final int ALPHABET_SET_SIZE=26;//tamano del alfabeto private static final int ALPHABET_SET_SIZE=26;//tamano del alfabeto
     public static final String AES_ALGORITHM="AES";
     public static final String TRANSFOMATION_AES_CBC="AES/CBC/PKCS5Padding";
     
     public static final String SHA_256_ALGORITHM="SHA-256"; 
     public static final String PBKDF2_WITH_HMAC_SHA_256_ALGORITHM="PBKDF2WithHmacSHA256";
     
     public static final String TRANSFOMATION_AES_GCM="AES/GCM/NoPadding";
     
     
     //tamaño de vector de inicializacio en AES CBC, 16 bytes
     public static final int AES_CBC_IV_LENGTH=16;
     
     //tamaño de vector de inicializacion en AES GCM, 12 bytes
     public static final int AES_GCM_IV_LENGTH=12;
     
     //longitud de TAG 128 bits
     public static final int TAG_LENGTH=128;
     
     
     
     //para el contenedor
     public static final String PKCS12_KEYSTORE_TYPE="PKCS12";
     public static final String CONTAINER_FILENAME="D:/contenedor.p12";
     public static final String CONTAINER_PASSWORD="container-password";
     //para la llave
     public static final String KEY_USE_PASSWORD="key-use-password";
     public static final String AES_ALIAS="aes_key";
     public static final int AES_KEY_SIZE_256=256;
     
     
     public static final String RSA_ALGORITHM="RSA";
     public static final int RSA_KEY_SIZE_2048=2048;
     
     public static final String ECC_ALGORITHM="EC";
     public static final String ECC_SIGN_ALGORITHM="SHA256withECDSA";
     
     public static final String ECC_KEY_PARAMETER_SPEC="secp256r1";
     
     public static final byte[] AES_GCM_ADD="2026-I-CC07".getBytes();
     
      //para certificado CA
     public static final String RSA_SIGN_ALGORITHM="SHA256withRSA";
     public static final String BCFIPS_PROVIDER="BCFIPS";
     public static final String CA_ROOT_ALIAS="ca_root_alias";
     
     //file names
     public static final String USER_HOME=System.getProperty("user.home");
     public static final String USER_CD_FILENAME=Paths.get(USER_HOME,"user-cd.crt").toString();
     public static final String CA_ROOT_CONTAINER_FILENAME=Paths.get(USER_HOME,"ca-root-container.p12").toString();
     
     //para usuario final
     public static final String END_USER_CONTAINER_FILENAME=Paths.get(USER_HOME,"end-user-container.p12").toString();
     public static final String END_USER_ALIAS="end_user_alias";
}


