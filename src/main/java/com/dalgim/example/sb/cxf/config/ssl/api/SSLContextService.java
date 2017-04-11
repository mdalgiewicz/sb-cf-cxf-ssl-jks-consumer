package com.dalgim.example.sb.cxf.config.ssl.api;

import javax.net.ssl.SSLContext;

/**
 * Created by Mateusz Dalgiewicz on 10.04.2017.
 */
public interface SSLContextService {

    SSLContext initSSLContext();
}
