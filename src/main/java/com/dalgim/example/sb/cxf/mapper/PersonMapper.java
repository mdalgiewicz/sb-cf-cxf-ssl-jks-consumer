package com.dalgim.example.sb.cxf.mapper;

import com.dalgim.example.sb.cxf.model.Person;
import com.dalgim.namespace.personservice.datatypes.PersonInfo;
import org.springframework.stereotype.Component;

/**
 * Created by dalgim on 05.04.2017.
 */
@Component
public class PersonMapper implements ObjectMapper<Person, PersonInfo> {

    @Override
    public PersonInfo map(Person person) {
        if (person == null) {
            return null;
        }
        PersonInfo personInfo = new PersonInfo();
        personInfo.setPassword(String.copyValueOf(person.getPassword()));
        personInfo.setFirstname(person.getFirstname());
        personInfo.setLastname(person.getLastname());
        personInfo.setLogin(person.getUsername());
        return personInfo;
    }

    @Override
    public Person reverseMap(PersonInfo personInfo) {
        if (personInfo == null) {
            return null;
        }
        return Person.builder()
                .firstname(personInfo.getFirstname())
                .lastname(personInfo.getLastname())
                .password(personInfo.getPassword() != null ? personInfo.getPassword().toCharArray() : null)
                .username(personInfo.getLogin())
                .build();
    }
}
