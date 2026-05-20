/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.service;

import static pe.edu.uni.fc.cc.common.Constants.ALPHABET_SET_SIZE;
import pe.edu.uni.fc.cc.common.Utils;

/**
 *
 * @author Usuario
 */
public class AffineChiperService {
    private final int a;
    private final int b;
    private final int inv_a;
    
    public AffineChiperService(int a,int b){
        if ((Utils.gcd(a,ALPHABET_SET_SIZE)!=1)||(a<0)||(b<0)) {
            //error por mcd(a,b)!=1
            throw new IllegalArgumentException("a debe ser coprimo con "+ALPHABET_SET_SIZE);
        }
        this.a=a%ALPHABET_SET_SIZE;
        this.b=b%ALPHABET_SET_SIZE;
        this.inv_a=Utils.getInverseModule(a,ALPHABET_SET_SIZE);
    }
    
    
     public String encrypt(String plainText){
        String result="";
        //barre todo la frase a cifrar
        for (int i = 0; i < plainText.length(); i++) {
            char c=plainText.charAt(i);
            //System.out.println(c);
            //solo encripta si es letra, si no hace nada y pasa
            if (Character.isLetter(c)) {
                //System.out.println(c);
                //offset para sumarle en asccii, distingue si es mayuscula o minuscula
                char offset=Character.isUpperCase(c)?'A':'a';
                //encryptado de un caracter
                c=(char)(((a*(c-offset)+b)%ALPHABET_SET_SIZE)+offset);
            }
            result+=c;
        }
        return result;
    }
     
     
      public String decrypt(String encryptText){
        String result="";
        //calculo de inverso modular
//        int inv_a=Utils.getInverseModule(a,ALPHABET_SET_SIZE);
  //      System.out.println("Inverso modular: " + inv_a);
        
         //barre todo la frase a decifrar
        for (int i = 0; i < encryptText.length(); i++) {
            //lee letra por letra
            char c=encryptText.charAt(i);
            //solo decrifa si es letra, si no hace nada y pasa
            if (Character.isLetter(c)) {
                //offset para sumarle en asccii, distingue si es mayuscula o minuscula
                char offset=Character.isUpperCase(c)?'A':'a';
                int tmp=inv_a*(c-offset-b+ALPHABET_SET_SIZE);
                if (inv_a == 0) return result;
                
                c=(char)(tmp%ALPHABET_SET_SIZE+offset);
            }
            //concadena la salida del desecriptado
            result+=c;
        }
        return result;
    }
    
    
    
    
    
    
    
}
