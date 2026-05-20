/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.uni.fc.cc.substitution;

/**
 *
 * @author Cesar Lopez A.
 */
public class Substitution {

    public static void main(String[] args) {
        
        //mensaje original a encriptar 
        String original_message="This is a plain text.";
        
        //longitud de llave o desplazamiento
        int key =5;
   
        //definicion de funciones de encriptacion y desencriptacion
        String encrypt_message=encrypt(original_message,key);//captura mensaje encriptado
        String decrypt_message=decrypt(encrypt_message,key);//captura mensaje desencriptado
        
        //impresion de mensajes por pantalla de cadenas origina, encriptada y desencriptada
        System.out.println("Mensaje original: "+original_message);
        System.out.println("Mensaje cifrado: "+encrypt_message);
        System.out.println("Mensaje descifrado: "+decrypt_message);
    }
    
    //desarrollo de funcion encriptacion
    private static String encrypt(String plainText, int key){
        
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
                   c-=26;//en cualquier caso de minuscula o mayuscula,resta la long del abacederio
                }
            }
            result+=c;
        }
        return result;
    }
    
    //desarrollo de funcion desencriptacion
    private static String decrypt(String encryptedText,int key){
        String result="";
        for (int i = 0; i < encryptedText.length(); i++) {
            char c=encryptedText.charAt(i);
            if(Character.isLetter(c)){
                c-=key;
                if(Character.isLowerCase(encryptedText.charAt(i)) && c<'a' ||
                   Character.isLowerCase(encryptedText.charAt(i)) && c<'A'){
                    c+=26;
                }
            }
            result+=c;
        }
        return result;
    }
}
