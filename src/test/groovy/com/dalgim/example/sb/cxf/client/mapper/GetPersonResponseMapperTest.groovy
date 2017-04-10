package com.dalgim.example.sb.cxf.client.mapper

import com.dalgim.namespace.personservice.datatypes.PersonInfo
import com.dalgim.namespace.personservice.general.GetPersonInfoResponse
import spock.lang.Specification

/**
 * Created by Mateusz Dalgiewicz on 07.04.2017.
 */
class GetPersonResponseMapperTest extends Specification {

    GetPersonInfoResponseMapper getPersonResponseMapper

    void setup() {
        getPersonResponseMapper = new GetPersonInfoResponseMapper(new PersonMapper())
    }

    def "Should map PersonInfoResponse into Person"() {
        given:
            def getPersonInfoResponse = new GetPersonInfoResponse()
            def personInfo = new PersonInfo()
            personInfo.setLogin('John.Smith')
            personInfo.setPassword('P@ss')
            personInfo.setFirstname('John')
            personInfo.setLastname('Smith')
            getPersonInfoResponse.setPersonInfo(personInfo)
        when:
            def person = getPersonResponseMapper.map(getPersonInfoResponse)
        then:
            person != null
            person.getFirstname() == personInfo.getFirstname()
            person.getLastname() == personInfo.getLastname()
            person.getPassword() == personInfo.getPassword() as char[]
            person.getUsername() == personInfo.getLogin()
    }

    def "should return null when GetPersonInfoResponse is null"() {
        expect:
            getGetPersonResponseMapper().map(null) == null
    }

    def "should return null when GetPersonInfoResponse.PersonInfo is null"() {
        expect:
            def getPersonInfoResponse = new GetPersonInfoResponse()
            getPersonResponseMapper.map(getPersonInfoResponse) == null
    }
}
