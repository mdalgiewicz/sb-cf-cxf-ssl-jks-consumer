package com.dalgim.example.sb.cxf.config.ssl;

import com.dalgim.example.sb.cxf.config.ssl.api.KeyManagerService;
import com.dalgim.example.sb.cxf.config.ssl.api.KeyStoreService;
import com.dalgim.example.sb.cxf.config.ssl.exception.SSLConfigurationRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

/**
 * Created by Mateusz Dalgiewicz on 10.04.2017.
 */
@Component
public class KeyManagerServiceImpl implements KeyManagerService {

    private final KeyStoreService keyStoreService;
    private final char[] keyManagerPassword;
    private final boolean isTrustedAll;

    @Autowired
    public KeyManagerServiceImpl(KeyStoreService keyStoreService,
                                 @Value("${client.ssl.keystore-key-pass}") char[] keyManagerPassword,
                                 @Value("${client.ssl.trust-all}") boolean isTrustedAll) {
        this.keyStoreService = keyStoreService;
        this.keyManagerPassword = keyManagerPassword;
        this.isTrustedAll = isTrustedAll;
    }

    @Override
    public KeyManager[] initKeyManagers() {
        String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(defaultAlgorithm);
            KeyStore keyStore = keyStoreService.initKeyStore();
            keyManagerFactory.init(keyStore, keyManagerPassword);
            return keyManagerFactory.getKeyManagers();
        } catch (NoSuchAlgorithmException e) {
            throw new SSLConfigurationRuntimeException("Error while getting instance of KeyManagerFactory: " + e);
        } catch (UnrecoverableKeyException | KeyStoreException e) {
            throw new SSLConfigurationRuntimeException("Error while initializing KeyManagerFactory: " + e);
        }
    }

    @Override
    public TrustManager[] initTrustManagers() {
        return isTrustedAll ? getTrustAllManagers() : getRealTrustManagers();
    }

    private TrustManager[] getRealTrustManagers() {
        String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
       try {
           TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
           KeyStore trustStore = keyStoreService.initTrustStore();
           trustManagerFactory.init(trustStore);
           return trustManagerFactory.getTrustManagers();
       } catch (NoSuchAlgorithmException e) {
           throw new SSLConfigurationRuntimeException("Error while getting instance of TrustManagerFactory: " + e);
       } catch (KeyStoreException e) {
           throw new SSLConfigurationRuntimeException("Error while initializing TrustManagerFactory: " + e);
       }
    }

    private TrustManager[] getTrustAllManagers() {
        return new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }};
    }

}
