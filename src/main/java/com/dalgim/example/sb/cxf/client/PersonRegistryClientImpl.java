package com.dalgim.example.sb.cxf.client;

import com.dalgim.example.sb.cxf.client.mapper.CreatePersonRequestMapper;
import com.dalgim.example.sb.cxf.client.mapper.GetAllPersonInfoResponseMapper;
import com.dalgim.example.sb.cxf.client.mapper.GetPersonInfoRequestMapper;
import com.dalgim.example.sb.cxf.client.mapper.GetPersonResponseMapper;
import com.dalgim.example.sb.cxf.model.Person;
import com.dalgim.namespace.personservice.PersonRegistry;
import com.dalgim.namespace.personservice.general.CreatePersonRequest;
import com.dalgim.namespace.personservice.general.GetAllPersonInfoResponse;
import com.dalgim.namespace.personservice.general.GetPersonInfoRequest;
import com.dalgim.namespace.personservice.general.GetPersonInfoResponse;
import com.dalgim.namespace.personservice.general.Void;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;
import com.google.common.base.Preconditions;

/**
 * Created by dalgim on 05.04.2017.
 */
@Service
@RequiredArgsConstructor
public class PersonRegistryClientImpl implements PersonRegistryClient {

    private final PersonRegistry personRegistry;
    private final CreatePersonRequestMapper createPersonRequestMapper;
    private final GetPersonResponseMapper getPersonResponseMapper;
    private final GetPersonInfoRequestMapper getPersonInfoRequestMapper;
    private final GetAllPersonInfoResponseMapper getAllPersonInfoResponseMapper;

    @Override
    public void addPerson(Person person) {
        Preconditions.checkNotNull(person, "Person cannot be null.");

        CreatePersonRequest createPersonRequest = createPersonRequestMapper.map(person);
        personRegistry.createPerson(createPersonRequest);
    }

    @Override
    public Person getPerson(String username) {
        Preconditions.checkNotNull(username, "Username cannot be null.");

        GetPersonInfoRequest getPersonInfoRequest = getPersonInfoRequestMapper.map(username);
        GetPersonInfoResponse getPersonInfoResponse = personRegistry.getPersonInfo(getPersonInfoRequest);
        return getPersonResponseMapper.map(getPersonInfoResponse);
    }

    @Override
    public Collection<Person> getAllPerson() {
        GetAllPersonInfoResponse allPersonInfo = personRegistry.getAllPersonInfo(new Void());
        return getAllPersonInfoResponseMapper.map(allPersonInfo);
    }
}
