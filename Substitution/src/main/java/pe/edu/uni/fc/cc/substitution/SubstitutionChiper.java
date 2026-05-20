/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution;

/**
 *
 * @author Usuario
 */
public class SubstitutionChiper {
    
    //mapa de caracteres
    static char[] charsMap={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
    
    private static int indexOfCharsMap(char c){
        int index=-1;
        for (int i = 0; i < charsMap.length; i++) {
            if(c==charsMap[i]){
                index=i;
                break;
            }
        }
        return index;
    }
    
    public static void main(String[] args) {
        System.out.println("Substituto Chiper!");
        System.out.println("Longitud del Map:"+charsMap.length);
        char c='g';
        System.out.println("index["+c+"]="+indexOfCharsMap(c));
        
        String original_message="This is a plain txt!";
        String encrypt_message=encrypt(original_message);
        String decrypt_message=decrypt(encrypt_message);
        
        System.out.println("Original:"+original_message);
        System.out.println("encrypt:"+encrypt_message);
        System.out.println("decrypt:"+decrypt_message);
    }
    
    private static String encrypt(String plaintText){
        String result="";
        int delta='Z'-'z';
        //System.out.println("delta:"+delta);
        for (int i = 0; i < plaintText.length(); i++) {
            char c=plaintText.charAt(i);
         //          System.out.println("c:"+c);
            if(Character.isLetter(c)){
                //System.out.println("c:"+c);
                if(Character.isUpperCase(c)){
                    c-=delta;
                    //encryptar
                    c=charsMap[c-'a'];
                    c+=delta;
                }else{//lowwercase
                    c=charsMap[c-'a'];
                }
            }
            result+=c;
        }
        return result;
    }
    private static String decrypt(String encryptedText){
        String result="";
        int delta='Z'-'z';
        for (int i = 0; i < encryptedText.length(); i++) {
            char c=encryptedText.charAt(i);
            if(Character.isLetter(c)){
                if(Character.isUpperCase(c)){
                    c-=delta;
                    c=(char)('a'+indexOfCharsMap(c));
                    c+=delta;
                }else{
                    c=(char)('a'+indexOfCharsMap(c));
                }
            }
            result+=c;
        }
        return result;
    }  
}
