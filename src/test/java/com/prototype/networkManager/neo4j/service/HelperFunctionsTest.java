package com.prototype.networkManager.neo4j.service;

import com.prototype.networkManager.neo4j.domain.DeviceType;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.services.HelperFunctions;
import com.prototype.networkManager.neo4j.services.HelperFunctionsImpl;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class HelperFunctionsTest {

    private final HelperFunctions helperFunctions = new HelperFunctionsImpl();

    @Test
    public void arePortNumberListUniqueTest(){
        List<Port> ports = new ArrayList<>();
        ports.add(new Port(1, DeviceType.None, "", null));
        ports.add(new Port(2, DeviceType.None, "", null));
        ports.add(new Port(3, DeviceType.None, "", null));

        int newPortNumber = 4;
        assertEquals(helperFunctions.arePortNumberListUnique(ports, newPortNumber), true);
        newPortNumber = 1;
        assertEquals(helperFunctions.arePortNumberListUnique(ports, newPortNumber), false);
    }
}
