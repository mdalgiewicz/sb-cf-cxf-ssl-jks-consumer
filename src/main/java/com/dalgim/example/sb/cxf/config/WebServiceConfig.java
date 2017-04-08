package com.dalgim.example.sb.cxf.config;

import com.dalgim.namespace.personservice.PersonRegistry;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by dalgim on 02.04.2017.
 */
@Configuration
public class WebServiceConfig {

    @Value(value = "classpath:ssl/consumer-keystore.jks")
    private Resource keystoreResource;
    @Value(value = "classpath:ssl/consumer-truststore.jks")
    private Resource truststoreResource;
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String TRUSTSTORE_TYPE = "JKS";
    //On production environment passwords should be encrypted
    private static final char[] KEYSTORE_PASSWORD = new char[] {'P', '@', 'S', 'S', 'W', 'O', 'R', 'D'};
    private static final char[] TRUSTSTORE_PASSWORD = new char[] {'P', '@', 's', 's', 'w', '0', 'r', 'd'};
    private static final char[] KMF_PASSWORD =  new char[] {'P', '@', 's', 's', 'w', '0', 'r', 'd'};
    private static final String SSL_PROTOCOL = "TLS";

    @Bean
    public PersonRegistry personRegistry(@Value("${personService.address}") String address) {
        PersonRegistry personRegistry = (PersonRegistry) jaxWsProxyFactoryBean(address).create();
        Client client = ClientProxy.getClient(personRegistry);
        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        SSLSocketFactory sslSocketFactory = sslContext().getSocketFactory();
        tlsClientParameters.setSSLSocketFactory(sslSocketFactory);
        httpConduit.setTlsClientParameters(tlsClientParameters);
        return personRegistry;
    }

    JaxWsProxyFactoryBean jaxWsProxyFactoryBean(String address) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(PersonRegistry.class);
        jaxWsProxyFactoryBean.setAddress(address);
        return jaxWsProxyFactoryBean;
    }

    SSLContext sslContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance(SSL_PROTOCOL);
            KeyManager[] keyManagers = keyManagerFactory().getKeyManagers();
            TrustManager[] trustManagers = trustManagerFactory().getTrustManagers();
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while getting instance of SSLContext: " + e);
        } catch (KeyManagementException e) {
            throw new RuntimeException("Error while initializing SSLContext: " + e);
        }
    }

    KeyManagerFactory keyManagerFactory() {
        String defaultAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(defaultAlgorithm);
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            keyStore.load(keystoreResource.getInputStream(), KEYSTORE_PASSWORD);
            keyManagerFactory.init(keyStore, KMF_PASSWORD);
            return keyManagerFactory;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while getting instance of KeyManagerFactory: " + e);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Error while getting instance of KeyStore: " + e);
        } catch (CertificateException | IOException e) {
            throw new RuntimeException("Error while loading KeyStore: " + e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException("Error while initializing KeyManagerFactory: " + e);
        }
    }

    TrustManagerFactory trustManagerFactory() {
        String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
            KeyStore trustStore = KeyStore.getInstance(TRUSTSTORE_TYPE);
            trustStore.load(truststoreResource.getInputStream(), TRUSTSTORE_PASSWORD);
            trustManagerFactory.init(trustStore);
            return trustManagerFactory;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while getting instance of TrustManagerFactory: " + e);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Error while getting instance of TrustStore: " + e);
        } catch (CertificateException | IOException e) {
            throw new RuntimeException("Error while loading TrustStore: " + e);
        }
    }

}
