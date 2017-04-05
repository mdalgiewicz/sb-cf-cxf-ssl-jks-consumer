package com.dalgim.example.sb.cxf.client.mapper;

import com.dalgim.namespace.personservice.general.GetPersonInfoRequest;
import org.springframework.stereotype.Component;

/**
 * Created by dalgim on 05.04.2017.
 */
@Component
public class GetPersonInfoRequestMapper implements NoReverseObjectMapper<String, GetPersonInfoRequest> {

    @Override
    public GetPersonInfoRequest map(String login) {
        if (login == null) {
            return null;
        }
        GetPersonInfoRequest getPersonInfoRequest = new GetPersonInfoRequest();
        getPersonInfoRequest.setLogin(login);
        return getPersonInfoRequest;
    }
}
