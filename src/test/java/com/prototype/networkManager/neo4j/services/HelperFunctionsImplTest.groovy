package com.prototype.networkManager.neo4j.services

import com.prototype.networkManager.neo4j.domain.Port
import spock.lang.Specification
import spock.lang.Unroll

class HelperFunctionsImplTest extends Specification {


    HelperFunctionsImpl helperFunctions = new HelperFunctionsImpl()

    @Unroll
    def "are port number list unique"() {

        expect:
        helperFunctions.arePortNumberListUnique(portList as List<Port>, newPortNumber) == expectedResult

        where:
        portList|newPortNumber|expectedResult
        portsTableFull()|6|true
        portsTableFull()|3|false
        portsTableEmpty()|1|true

    }

    def "portsTableFull"() {
        return [new Port(1,null,null,null,null,false,null),
                new Port(2,null,null,null,null,false,null),
                new Port(3,null,null,null,null,false,null),
                new Port(4,null,null,null,null,false,null),
                new Port(5,null,null,null,null,false,null)]
    }

    def "portsTableEmpty"() {
        return []
    }
}
