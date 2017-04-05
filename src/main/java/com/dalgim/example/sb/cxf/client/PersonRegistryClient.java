package com.dalgim.example.sb.cxf.client;

import com.dalgim.example.sb.cxf.model.Person;

import java.util.Collection;

/**
 * Created by dalgim on 05.04.2017.
 */
public interface PersonRegistryClient {

    void addPerson(Person person);
    Person getPerson(String username);
    Collection<Person> getAllPerson();
}
