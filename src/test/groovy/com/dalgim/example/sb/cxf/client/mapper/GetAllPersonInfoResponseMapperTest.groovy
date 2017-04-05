package com.dalgim.example.sb.cxf.client.mapper

import com.dalgim.example.sb.cxf.model.Person
import com.dalgim.namespace.personservice.datatypes.PersonInfo
import com.dalgim.namespace.personservice.general.GetAllPersonInfoResponse
import spock.lang.Specification

/**
 * Created by dalgim on 05.04.2017.
 */
class GetAllPersonInfoResponseMapperTest extends Specification {

    GetAllPersonInfoResponseMapper getAllPersonInfoResponseMapper
    PersonMapper personMapper

    void setup() {
        personMapper = Mock(PersonMapper)
        getAllPersonInfoResponseMapper = new GetAllPersonInfoResponseMapper(personMapper)
    }

    def "should map GetAllPersonInfoResponse into List<Person>"() {
        given:
            def getAllPersonInfoResponse = new GetAllPersonInfoResponse()
            def personInfo1 = new PersonInfo(
                    firstname: 'Jenny',
                    lastname: 'Johnson',
                    login: 'Jenny.Johnson',
                    password: 'P@ss')
            def personInfo2 = new PersonInfo(
                    firstname: 'John',
                    lastname: 'Smith',
                    login: 'John.Smith',
                    password: 'P@SS')
            def personInfoList = (List)[personInfo1, personInfo2]
            getAllPersonInfoResponse.getPersonInfoList().addAll(personInfoList)
        and:
            char[] password1 = ['p', '@', 's', 's']
            def person1 = Person.builder()
                    .firstname('Jenny')
                    .lastname('Johnson')
                    .username('Jenny.Johnson')
                    .password(password1)
                    .build()
            personMapper.reverseMap(personInfo1) >> person1
            char[] password2 = ['p', '@', 'S', 'S']
            def person2 = Person.builder()
                    .firstname('John')
                    .lastname('Smith')
                    .username('John.Smith')
                    .password(password2)
                    .build()
            personMapper.reverseMap(personInfo2) >> person2
        when:
            def personList = getAllPersonInfoResponseMapper.map(getAllPersonInfoResponse)
        then:
            personList != null
            personList.size() == 2
            personList.contains(person1)
            personList.contains(person2)
    }

    def "should return null when GetAllPersonInfoResponse is null"() {
        expect:
            getAllPersonInfoResponseMapper.map(null) == null
    }
}
