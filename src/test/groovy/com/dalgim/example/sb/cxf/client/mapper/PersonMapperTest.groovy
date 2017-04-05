package com.dalgim.example.sb.cxf.client.mapper

import com.dalgim.example.sb.cxf.model.Person
import com.dalgim.namespace.personservice.datatypes.PersonInfo
import spock.lang.Specification

/**
 * Created by dalgim on 05.04.2017.
 */
class PersonMapperTest extends Specification {

    PersonMapper personMapper

    void setup() {
        personMapper = new PersonMapper()
    }

    def "should map Person into PersonInfo"() {
        given:
            char[] password = ['p', '@', 's', 's']
            def person = Person.builder()
                    .firstname('John')
                    .lastname('Smith')
                    .username('John.Smith')
                    .password(password)
                    .build()
        when:
            def personInfo = personMapper.map(person)
        then:
            personInfo != null
            personInfo.getFirstname() == person.getFirstname()
            personInfo.getLastname() == person.getLastname()
            personInfo.getLogin() == person.getUsername()
            personInfo.getPassword() == person.getPassword() as String
    }

    def "should return null when map Person into PersonInfo and Person is null"() {
        expect:
            personMapper.map(null) == null
    }

    def "should map PersonInfo into Person"() {
        given:
            def personInfo = new PersonInfo()
            personInfo.setLogin('John.Smith')
            personInfo.setFirstname('John')
            personInfo.setLastname('Smith')
            personInfo.setPassword('P@ssw0rd')
        when:
            def person = personMapper.reverseMap(personInfo)
        then:
            person != null
            person.getFirstname() == personInfo.getFirstname()
            person.getLastname() == personInfo.getLastname()
            person.getUsername() == personInfo.getLogin()
            person.getPassword() == personInfo.getPassword() as char[]
    }

    def "should return null when map PersonInfo into Person and PersonInfo is null"() {
        expect:
            personMapper.reverseMap(null) == null
    }
}
