/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.service;

/**
 *
 * @author Usuario
 */
public class SubstitutionChiperService {
   //mapa de caracteres
   private final char[] key;//={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
   
   public SubstitutionChiperService(char[] key){
       this.key=key;
   }
   
    private int indexOfCharsMap(char c){
        int index=-1;
        for (int i = 0; i < key.length; i++) {
            if(c==key[i]){
                index=i;
                break;
            }
        }
        return index;
    }
    
    public String encrypt(String plaintText){
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
                    c=key[c-'a'];
                    c+=delta;
                }else{//lowwercase
                    c=key[c-'a'];
                }
            }
            result+=c;
        }
        return result;
    }
    
    public String decrypt(String encryptedText){
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
