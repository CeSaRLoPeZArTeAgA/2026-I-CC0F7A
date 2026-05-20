/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.App;

import pe.edu.uni.fc.cc.substitution.service.SubstitutionChiperService;

/**
 *
 * @author Usuario
 */
public class MainSubstitutionChiper {
    //mapa de caracteres
    static char[] key={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
    
    public static void main(String[] args) {
        SubstitutionChiperService chiper=new SubstitutionChiperService(key);
    
        System.out.println("MainSubstitutionChiper!!!");
        String original_message="This is a plain txt!";
        String encrypt_message=chiper.encrypt(original_message);
        String decrypt_message=chiper.decrypt(encrypt_message);
        
        System.out.println("Original:"+original_message);
        System.out.println("encrypt:"+encrypt_message);
        System.out.println("decrypt:"+decrypt_message);
    }
}
