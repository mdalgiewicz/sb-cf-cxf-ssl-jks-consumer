package com.dalgim.example.sb.cxf.client.mapper;

import com.dalgim.example.sb.cxf.model.Person;
import com.dalgim.namespace.personservice.datatypes.PersonInfo;
import com.dalgim.namespace.personservice.general.GetPersonInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by dalgim on 05.04.2017.
 */
@Component
@RequiredArgsConstructor
public class GetPersonInfoResponseMapper implements NoReverseObjectMapper<GetPersonInfoResponse, Person> {

    private final PersonMapper personMapper;

    @Override
    public Person map(GetPersonInfoResponse getPersonInfoResponse) {
        if (getPersonInfoResponse == null) {
            return null;
        }
        PersonInfo personInfo = getPersonInfoResponse.getPersonInfo();
        if (personInfo == null) {
            return null;
        }
        return personMapper.reverseMap(personInfo);
    }
}
