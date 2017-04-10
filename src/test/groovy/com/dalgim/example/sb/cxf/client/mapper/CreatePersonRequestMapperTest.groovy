package com.dalgim.example.sb.cxf.client.mapper

import com.dalgim.example.sb.cxf.model.Person
import spock.lang.Specification

/**
 * Created by Mateusz Dalgiewicz on 05.04.2017.
 */
class CreatePersonRequestMapperTest extends Specification {

    CreatePersonRequestMapper createPersonRequestMapper

    void setup() {
        createPersonRequestMapper = new CreatePersonRequestMapper()
    }

    def "should map CreatePersonRequest into Person"() {
        given:
            char[] password = ['p', '@', 's', 's']
            def person = Person.builder()
                    .firstname('John')
                    .lastname('Smith')
                    .username('John.Smith')
                    .password(password)
                    .build()
        when:
            def createPersonRequest = createPersonRequestMapper.map(person)
        then:
            createPersonRequest != null
            createPersonRequest.getFirstname() == person.getFirstname()
            createPersonRequest.getLogin() == person.getUsername()
            createPersonRequest.getLastname() == person.getLastname()
            createPersonRequest.getPassword() == person.getPassword() as String
    }

    def "should return null while CreatePersonRequest is null"() {
        expect:
            createPersonRequestMapper.map(null) == null
    }
}
