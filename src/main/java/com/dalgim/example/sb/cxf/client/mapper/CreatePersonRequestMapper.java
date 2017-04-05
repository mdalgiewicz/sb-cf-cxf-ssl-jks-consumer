package com.dalgim.example.sb.cxf.client.mapper;

import com.dalgim.example.sb.cxf.model.Person;
import com.dalgim.namespace.personservice.general.CreatePersonRequest;
import org.springframework.stereotype.Component;

/**
 * Created by dalgim on 05.04.2017.
 */
@Component
public class CreatePersonRequestMapper implements NoReverseObjectMapper<Person, CreatePersonRequest> {

    @Override
    public CreatePersonRequest map(Person person) {
        if (person == null) {
            return null;
        }
        CreatePersonRequest createPersonRequest = new CreatePersonRequest();
        createPersonRequest.setFirstname(person.getFirstname());
        createPersonRequest.setLogin(person.getUsername());
        createPersonRequest.setPassword(person.getPassword() != null ? String.valueOf(person.getPassword()) : null);
        return createPersonRequest;
    }
}
