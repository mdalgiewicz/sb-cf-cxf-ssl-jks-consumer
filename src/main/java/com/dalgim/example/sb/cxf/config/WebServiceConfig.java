package com.dalgim.example.sb.cxf.config;

import com.dalgim.example.sb.cxf.config.ssl.api.SSLContextService;
import com.dalgim.namespace.personservice.PersonRegistry;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.net.ssl.SSLContext;

/**
 * Created by Mateusz Dalgiewicz on 02.04.2017.
 */
@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {

    private final SSLContextService sslContextService;

    @Bean
    public PersonRegistry personRegistry(@Value("${client.producer-address}") String address) {
        PersonRegistry personRegistry = (PersonRegistry) jaxWsProxyFactoryBean(address).create();
        Client client = ClientProxy.getClient(personRegistry);
        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        SSLContext sslContext = sslContextService.initSSLContext();
        tlsClientParameters.setSSLSocketFactory(sslContext.getSocketFactory());
        httpConduit.setTlsClientParameters(tlsClientParameters);
        return personRegistry;
    }

    JaxWsProxyFactoryBean jaxWsProxyFactoryBean(String address) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(PersonRegistry.class);
        jaxWsProxyFactoryBean.setAddress(address);
        return jaxWsProxyFactoryBean;
    }

}
