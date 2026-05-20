/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.App;

import pe.edu.uni.fc.cc.substitution.service.AffineChiperService;

/**
 *
 * @author Usuario
 */
public class MainAffineChiper {
    public static void main(String[] args) {
        System.out.println("Affine Cipher Main!");
        String original="This is Plain text!";
        
        //key (a,b)
        int a=11;
        int b=6;
        
        AffineChiperService AffineChiper=new AffineChiperService(a,b);
        //encriptado y desencriptando
        String encrypt=AffineChiper.encrypt(original);
        String decrypt=AffineChiper.decrypt(encrypt);
        
        //impresion de resultados
        System.out.println("Original: "+original);
        System.out.println("Cifrado: "+encrypt);
        System.out.println("Descrifado: "+decrypt);
    }
}
