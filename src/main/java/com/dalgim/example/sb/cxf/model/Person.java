package com.dalgim.example.sb.cxf.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Mateusz Dalgiewicz on 05.04.2017.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Person {

    private String username;
    private String firstname;
    private String lastname;
    private char[] password;
}
