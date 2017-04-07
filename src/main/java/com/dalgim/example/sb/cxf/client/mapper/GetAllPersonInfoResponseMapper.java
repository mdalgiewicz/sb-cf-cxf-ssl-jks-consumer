package com.dalgim.example.sb.cxf.client.mapper;

import com.dalgim.example.sb.cxf.model.Person;
import com.dalgim.namespace.personservice.general.GetAllPersonInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dalgim on 05.04.2017.
 */
@Component
@RequiredArgsConstructor
public class GetAllPersonInfoResponseMapper implements NoReverseObjectMapper<GetAllPersonInfoResponse, List<Person>> {

    private final PersonMapper personMapper;

    @Override
    public List<Person> map(GetAllPersonInfoResponse getAllPersonInfoResponse) {
        if (getAllPersonInfoResponse == null) {
            return null;
        }
        return getAllPersonInfoResponse.getPersonInfoList()
                .stream()
                .map(personMapper::reverseMap)
                .collect(Collectors.toList());
    }
}
