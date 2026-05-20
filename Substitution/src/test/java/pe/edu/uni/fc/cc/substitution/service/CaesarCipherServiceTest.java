/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.substitution.service;

import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;// deberia invocarse solo las funciones utilizadas
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
/**
 *
 * @author Usuario
 */
public class CaesarCipherServiceTest {
    
   private final CaesarCipherService cipher= new  CaesarCipherService(5);
   
   @Test
   public void testEncryptBasic(){
       String input="abc";//cadena de entrada
       String esperado="fgh";//cadena que debe salir
       //compara la cadena que debe salir, con la cadena que debe encriptar
       assertEquals(esperado,cipher.encrypt(input));    
   }
   
   @Test
   public void testDecryptBasic(){
       String input="abc";//cadena de entrada
       String output="fgh";//cadena que debe salir
       //compara la cadena que inicial, con la cadena que debe desencriptar
       assertEquals(input,cipher.decrypt(output));    
   }
   
   @Test
   public void testEncryptDecryptIdenty(){
       String input="Hola Cesar Lopez A.";//cadena de entrada
       String encrypted=cipher.encrypt(input);//cadena que debe salir despues de encriptar
       //compara la cadena que inicial, con la cadena que debe desencriptar
       assertEquals(input,cipher.decrypt(encrypted));   
   }
   
   @Test
   public void testWrapAround(){
       //para revisar en el limete del alfabeto
       String input="xyz";
       String excepted="cde";
       assertEquals(excepted,cipher.encrypt(input));
   }
   
   @Test
   public void testNonLetter(){
       //para revisar en el limete del alfabeto y simbolo que no son letras
       String input="xyz Hola Cesar1979$%";
       String encrypted=cipher.encrypt(input);
       assertTrue(encrypted.contains("$"));
       assertTrue(encrypted.contains(" "));
       assertTrue(encrypted.contains("%"));
       assertFalse(encrypted.contains("."));
   }
   
   @Test
   public void testUpperCase(){
       //revisa las mayusculas
        String input="ABZ";
        String expected="FGE";
        assertEquals(expected,cipher.encrypt(input));
   }
   
}
