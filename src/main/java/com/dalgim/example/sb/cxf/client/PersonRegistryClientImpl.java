package com.dalgim.example.sb.cxf.client;

import com.dalgim.example.sb.cxf.model.Person;
import com.dalgim.namespace.personservice.PersonRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;

/**
 * Created by dalgim on 05.04.2017.
 */
@Service
@RequiredArgsConstructor
public class PersonRegistryClientImpl implements PersonRegistryClient {

    private final PersonRegistry personRegistry;

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public Person getPerson(String username) {
        return null;
    }

    @Override
    public Collection<Person> getAllPerson() {
        return null;
    }
}
