/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.hash;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static pe.edu.uni.fc.cc.common.Constants.SHA_256_ALGORITHM;
import pe.edu.uni.fc.cc.common.Utils;

/**
 *
 * @author Usuario
 */
public class HashingPlain {

    public static void main(String[] args) {
        System.out.println("Hashing!!!");
        
        try {
            MessageDigest md=MessageDigest.getInstance(SHA_256_ALGORITHM);
            InputStream in=HashingPlain.class.getResourceAsStream(HashingPlain.class.getSimpleName()+".class");
            final byte[] bytes=new byte[1024];
            
            for (int length = in.read(bytes); length!= -1; length=in.read(bytes)) {
                md.update(bytes,0,length);
            }
            
            final byte[] hashed=md.digest();
            System.out.println("el valor SHA 256 es:"+Utils.byteToHex(hashed));
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(HashingPlain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(HashingPlain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
