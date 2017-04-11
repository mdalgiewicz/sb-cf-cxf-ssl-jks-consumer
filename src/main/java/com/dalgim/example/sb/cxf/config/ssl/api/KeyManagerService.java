package com.dalgim.example.sb.cxf.config.ssl.api;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

/**
 * Created by Mateusz Dalgiewicz on 10.04.2017.
 */
public interface KeyManagerService {

    KeyManager[] initKeyManagers();
    TrustManager[] initTrustManagers();
}
