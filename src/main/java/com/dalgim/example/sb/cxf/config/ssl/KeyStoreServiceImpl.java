package com.dalgim.example.sb.cxf.config.ssl;

import com.dalgim.example.sb.cxf.config.ClasspathFileLoader;
import com.dalgim.example.sb.cxf.config.ssl.api.KeyStoreService;
import com.dalgim.example.sb.cxf.config.ssl.exception.SSLConfigurationRuntimeException;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by Mateusz Dalgiewicz on 10.04.2017.
 */
@Component
public class KeyStoreServiceImpl implements KeyStoreService {

    private final String keystoreLocation;
    private final char[] keystorePassword;
    private final String truststoreLocation;
    private final char[] truststorePassword;
    private final String keystoreType;
    private final String truststoreType;

    @Autowired
    public KeyStoreServiceImpl(@Value("${client.ssl.keystore}") String keystoreLocation,
                               @Value("${client.ssl.keystore-pass}") char[] keystorePassword,
                               @Value("${client.ssl.truststore}") String truststoreLocation,
                               @Value("${client.ssl.truststore-pass}") char[] truststorePassword,
                               @Value("${client.ssl.keystore-type}") String keystoreType,
                               @Value("${client.ssl.truststore-type}") String truststoreType) {

        this.keystoreLocation = keystoreLocation;
        this.keystorePassword = keystorePassword;
        this.truststoreLocation = truststoreLocation;
        this.truststorePassword = truststorePassword;
        this.keystoreType = keystoreType;
        this.truststoreType = truststoreType;
    }

    @Override
    public KeyStore initKeyStore() {
        return initKeyStore(keystoreLocation, keystorePassword, keystoreType);
    }

    @Override
    public KeyStore initTrustStore() {
        return initKeyStore(truststoreLocation, truststorePassword, truststoreType);
    }

    private KeyStore initKeyStore(String keystoreLocation, char[] password, String keystoreType) {
        Preconditions.checkNotNull(keystoreLocation, "KeyStore location cannot be null!");
        Preconditions.checkNotNull(password, "KeyStore password cannot be null!");

        try {
            KeyStore keyStore = KeyStore.getInstance(keystoreType);
            keyStore.load(ClasspathFileLoader.loadFile(keystoreLocation), password);
            return keyStore;
        } catch (IOException e) {
            throw new SSLConfigurationRuntimeException("Error while getting keystore from file path: " + e);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new SSLConfigurationRuntimeException("Error while getting instance of keystore: " + e);
        }
    }
}
