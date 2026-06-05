/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.digitalsignature.service;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author Usuario
 */
public class ProtocolDigitalSignatureService {
    public BigInteger signDigest(BigInteger hash,RSAPrivateKey privateKey){
        
        if(hash==null||privateKey==null) return null;
        
        BigInteger d = privateKey.getPrivateExponent();
        BigInteger n=privateKey.getModulus();
        return hash.modPow(d, n);
    }
    
    public BigInteger recoverDigest(BigInteger signature,RSAPublicKey publicKey){
        if(signature==null||publicKey==null) return null;
        BigInteger e=publicKey.getPublicExponent();
        BigInteger n=publicKey.getModulus();
        
        return signature.modPow(e, n);
    }
    
    public boolean verify(BigInteger recoveredHash,BigInteger calculatedHash){
        if(recoveredHash==null|| calculatedHash==null) return false;
        return recoveredHash.equals(calculatedHash);
    }
    
    
}
