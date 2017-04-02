package com.dalgim.example.sb.cxf.config;

import com.dalgim.namespace.personservice.PersonRegistry;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dalgim on 02.04.2017.
 */
@Configuration
public class WebConfig {

    @Bean
    public PersonRegistry personRegistry(@Value("${personService.address}") String address) {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(PersonRegistry.class);
        jaxWsProxyFactoryBean.setAddress(address);
        return (PersonRegistry) jaxWsProxyFactoryBean.create();
    }
}
