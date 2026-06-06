/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.uni.fc.cc.tlsprotocol;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import static pe.edu.uni.fc.cc.common.Constants.KEY_USE_PASSWORD;
import static pe.edu.uni.fc.cc.common.Constants.PKCS12_KEYSTORE_TYPE;
import static pe.edu.uni.fc.cc.common.Constants.SERVER_TLS_FILENAME;
import static pe.edu.uni.fc.cc.common.Constants.TLS_PORT;
import static pe.edu.uni.fc.cc.common.Constants.TLS_VERSION_1_3;

/**
 *
 * @author Usuario
 */
public class TlsServer {
    public static void main(String[] args) {
        System.out.println("Tls Server!");
            
        //datos de la comunicacion
        int puerto = TLS_PORT;
        String keyStorePath=SERVER_TLS_FILENAME;
        String keyStorePassword= KEY_USE_PASSWORD;
           
        try {
            //cargar contenedor
            KeyStore ks=KeyStore.getInstance(PKCS12_KEYSTORE_TYPE);
            ks.load(new FileInputStream(keyStorePath),keyStorePassword.toCharArray());
            KeyManagerFactory kmf=KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            
            //inicializar la fabrica
            kmf.init(ks,keyStorePassword.toCharArray());
            SSLContext sslContext=SSLContext.getInstance(TLS_VERSION_1_3);
           
            //inicializar contexto
            sslContext.init(kmf.getKeyManagers(),null, null);
            SSLServerSocketFactory ssf=sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket=(SSLServerSocket) ssf.createServerSocket(puerto);
            
            //configuracion de forma estricta el uso de TLS 
            serverSocket.setEnabledProtocols(new String[] {TLS_VERSION_1_3});
            System.out.println("[Servidor] Inicio de la escucha de conexiones seguras TLS 1.3 en el puert "+ puerto);
            
            //bucle que permite conectar multiples veces
            while(true){
                //hilo principal
                SSLSocket socketClient = (SSLSocket) serverSocket.accept();
                System.out.println("[Servidor] Nueva Solicitud de conexion entrante");
                //delegar la conexion a un nuevo hilo
                new Thread(new ClientHandler(socketClient)).start();
            }
            
        } catch (KeyStoreException ex) {
            System.getLogger(TlsServer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(TlsServer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(TlsServer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (CertificateException ex) {
            System.getLogger(TlsServer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (KeyManagementException ex) {
            System.getLogger(TlsServer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (UnrecoverableKeyException ex) {
            System.getLogger(TlsServer.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }      
    }
}

class ClientHandler implements Runnable{
        private final SSLSocket socket;

        ClientHandler(SSLSocket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
}


