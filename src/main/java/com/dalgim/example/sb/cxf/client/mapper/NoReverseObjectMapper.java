package com.dalgim.example.sb.cxf.client.mapper;

/**
 * Created by dalgim on 05.04.2017.
 */
public interface NoReverseObjectMapper<T, K> {

    K map(T t);
}
