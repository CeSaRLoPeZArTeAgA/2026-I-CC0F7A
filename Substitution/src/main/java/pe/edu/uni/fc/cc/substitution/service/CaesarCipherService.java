/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.service;

//import static pe.edu.uni.fc.cc.substitution.Constants.ALPHABET_SET_SIZE;

import static pe.edu.uni.fc.cc.common.Constants.ALPHABET_SET_SIZE;




/**
 *
 * @author Usuario
 */
public class CaesarCipherService {
    
    private final int key;//llave a ser consumida en el servicio
    
    //cosntructor
    public CaesarCipherService(int key){
        //this.key=key; ¿que pasa si es mas de 26?
        this.key=key%ALPHABET_SET_SIZE;
    }
    
    //desarrollo de funcion encriptacion
    public String encrypt(String plainText){
        //guarda el resultado de la encriptacion en la cadena result
        String result="";//inicializacion con cadena vacia
        
        //visita todas las letras del texto original
        for (int i = 0; i < plainText.length(); i++) {
            //captura la letra de la cadena original, en la posicion i
            char c=plainText.charAt(i);
            
            // System.out.println("c: "+c);
            //pregunta si el carácter c es una letra
            if(Character.isLetter(c)){
                c+=key;//a la letra c suma la llave
                //pregunta si la letra es minuscula y la c paso z, de igual para mayuscula  
                if(Character.isLowerCase(plainText.charAt(i)) && c>'z' ||
                   Character.isUpperCase(plainText.charAt(i)) && c>'Z' ){
                   c-=ALPHABET_SET_SIZE;//en cualquier caso de minuscula o mayuscula,resta la long del abacederio
                }
            }
            result+=c;
        }
        return result;
    }
    
    //desarrollo de funcion desencriptacion
    public String decrypt(String encryptedText){
        String result="";
        for (int i = 0; i < encryptedText.length(); i++) {
            char c=encryptedText.charAt(i);
            if(Character.isLetter(c)){
                c-=key;//a la letra c suma la llave
                //pregunta si la letra es minuscula y la c esta antes de a, de igual para mayuscula 
                if(Character.isLowerCase(encryptedText.charAt(i)) && c<'a' ||
                   Character.isLowerCase(encryptedText.charAt(i)) && c<'A'){
                    c+=ALPHABET_SET_SIZE;//en cualquier caso de minuscula o mayuscula,resta la long del abacederio
                }
            }
            result+=c;
        }
        return result;
    }
    
}
