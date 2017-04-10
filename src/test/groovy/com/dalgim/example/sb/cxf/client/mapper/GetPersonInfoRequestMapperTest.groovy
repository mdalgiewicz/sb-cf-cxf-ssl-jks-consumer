package com.dalgim.example.sb.cxf.client.mapper

import spock.lang.Specification

/**
 * Created by Mateusz Dalgiewicz on 07.04.2017.
 */
class GetPersonInfoRequestMapperTest extends Specification {

    GetPersonInfoRequestMapper getPersonInfoRequestMapper

    void setup() {
        getPersonInfoRequestMapper = new GetPersonInfoRequestMapper()
    }

    def "should map login into PersonInfoRequest"() {
        given:
            def login = 'john.smith'
        when:
            def personInfoRequest = getPersonInfoRequestMapper.map(login)
        then:
            personInfoRequest != null
            personInfoRequest.getLogin() == login
    }

    def "should return null when login is null"() {
        expect:
            getPersonInfoRequestMapper.map(null) == null
    }
}
