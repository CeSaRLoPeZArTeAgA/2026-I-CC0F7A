/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.digitalSignature.app;

import com.mycompany.digitalsignature.ProtocolDigitalSignature;
import com.mycompany.digitalsignature.service.HashingService;
import com.mycompany.digitalsignature.service.ProtocolDigitalSignatureService;
import com.mycompany.digitalsignature.service.RSAKeyGeneratorService;
import java.math.BigInteger;

/**
 *
 * @author Usuario
 */
public class MainProtocolDigitalSignature {
    public static void main(String[] args) {
        System.out.println("Main Protocol Signature!!!");
        String originalMessage="Main Protocol Digital Signature !!!";
        
        //instanciar los servicios
        RSAKeyGeneratorService rsaKeyGeneratorService=new RSAKeyGeneratorService();
        HashingService hashingService= new HashingService();
        ProtocolDigitalSignatureService protocolDigitalSignatureService= new ProtocolDigitalSignatureService();
        
        //generar llaves
        rsaKeyGeneratorService.generateKeyPair();
        if(rsaKeyGeneratorService.getRsaPrivateKey()==null){
            System.out.println("[Error] No se pudieron generar las llavves criptograficass");
            return;
        }
        
        //calcular el hash del mensaje original
        BigInteger originalHash= hashingService.calculateHash(originalMessage);
        if(originalHash==null){
            System.out.println("[Error] No se pudo generar el hash ssolicitado!");
        }
        
        //firmar el hash
        BigInteger signedDigest=protocolDigitalSignatureService.signDigest(originalHash,rsaKeyGeneratorService.getRsaPrivateKey());
        
        //emisso envia al receptor el dato y la firma del dato
        //recupeamos el hash desde el datoss firmados
        BigInteger recoverDigest=protocolDigitalSignatureService.recoverDigest(signedDigest, rsaKeyGeneratorService.getRsaPublicKey());
        
        //calculamos el nuevo hash digest
        BigInteger newCalculatedDigest=hashingService.calculateHash(originalMessage);
        
        //verificar
        boolean verified=protocolDigitalSignatureService.verify(recoverDigest, newCalculatedDigest);
        
        //resultados
        System.out.println("Resumen recuperados: " + recoverDigest.toString());
        System.out.println("Resumen calculado: "+newCalculatedDigest.toString());
        System.out.println("Firma verificada: "+verified);
    }
}
