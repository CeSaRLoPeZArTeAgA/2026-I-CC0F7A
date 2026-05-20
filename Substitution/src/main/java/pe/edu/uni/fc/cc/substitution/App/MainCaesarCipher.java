/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.App;

import pe.edu.uni.fc.cc.substitution.service.CaesarCipherService;

/**
 *
 * @author Usuario
 */
public class MainCaesarCipher {
    public static void main(String[] args) {
        int key=5;
        CaesarCipherService cipher=new CaesarCipherService(key);
        String original="Este es texto plano";
        String encrypted=cipher.encrypt(original);
        String decrypted=cipher.decrypt(encrypted);
        
        //impresion en consola
        System.out.println("Original: "+original);
        System.out.println("Encrypted: "+encrypted);
        System.out.println("Decrypted: "+decrypted);
    }
}
