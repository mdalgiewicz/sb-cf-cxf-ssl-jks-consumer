package com.dalgim.example.sb.cxf.client.mapper;

/**
 * Created by Mateusz Dalgiewicz on 05.04.2017.
 */
public interface ObjectMapper<T, K> {

    K map(T t);
    T reverseMap(K k);
}
